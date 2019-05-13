package com.gateway.common.support;

import com.gateway.common.JaxwayClientAuthenticationDataStore;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.defaults.LocalJaxwayAuthenticationClientDataStore;
import com.gateway.common.support.http.HttpUtil;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.common.support.http.JaxHttpResponseWrapper;
import com.gateway.common.util.VersionUtil;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author huaili
 * @Date 2019/4/23 17:58
 * @Description DefaultJaxClientLongPullService
 **/
public class DefaultJaxClientLongPullService implements JaxClientLongPullService, DisposableBean {

    private Log logger = new DefaultLogImpl(getClass());

    private JaxwayClientAuthenticationDataStore jaxwayAuthenticationDataStore;

    private ExecutorService executorService;

    private RateLimiter longPollRateLimiter;

    private double longPullQPS = 0.01;

    private static String GROUP_NAME = "JaxWay";

    private static String JAX_PORTAL_HOST_PROPERTIES_NAME = "jaxway.host";

    private static String JAX_APP_ID_PROPERTIES_NAME = "jaxway.appid";

    private static String REQUEST_TEMPLATE = "%s/client/getAppInfo?appid=%s&versionId=%s";

    private Environment env;

    private List<String> hosts;

    private String appId;

    private LoadBalanceService loadBalanceService;

    private int connectTimeout = 800;

    private int readTimeOut = 1500;

    public DefaultJaxClientLongPullService(Environment env, LoadBalanceService loadBalanceService) {
        Assert.notNull(env.getProperty(JAX_PORTAL_HOST_PROPERTIES_NAME), "jaxway.host has not set");
        Assert.notNull(env.getProperty(JAX_APP_ID_PROPERTIES_NAME), "jaxway.appid has not set");

        this.loadBalanceService = loadBalanceService;
        this.env = env;
        this.hosts = Arrays.asList(this.env.getProperty(JAX_PORTAL_HOST_PROPERTIES_NAME).split(","));
        this.appId = this.env.getProperty(JAX_APP_ID_PROPERTIES_NAME);
        this.executorService = Executors.newSingleThreadExecutor(JaxwayThreadFactory.create(GROUP_NAME, true));
        this.longPollRateLimiter = RateLimiter.create(longPullQPS);
        this.jaxwayAuthenticationDataStore = LocalJaxwayAuthenticationClientDataStore.instance();

        /**
         * begin long pull for app authority info
         */
        doLongPull(this.jaxwayAuthenticationDataStore);
    }

    public DefaultJaxClientLongPullService(Environment env) {
        this(env, LoadBalanceService.RandomLoadBalanceService);
    }


    @Override
    public void doLongPull(JaxwayClientAuthenticationDataStore jaxwayAuthenticationDataStore) {
        HttpUtil httpUtil = HttpUtil.newInstance();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long versionId = -1;// pull the whole info
                while (!Thread.currentThread().isInterrupted()) {
                    String requestUrl = generateUrl(selectPortalHost(),versionId);
                    if (!longPollRateLimiter.tryAcquire(5, TimeUnit.MILLISECONDS)) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }

                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();
                    try {
                        ParameterizedTypeReference<JaxHttpResponseWrapper<List<JaxClientAuthentication>>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<List<JaxClientAuthentication>>>() {};
                        JaxHttpResponseWrapper<List<JaxClientAuthentication>> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

                        if(responseWrapper == null){
                            throw new NullPointerException("response JaxClientAuthentications is null");
                        }

                        if (responseWrapper.getCode() == 200) {
                            List<JaxClientAuthentication> jaxClientAuthentications = responseWrapper.getBody();

                            if(jaxClientAuthentications == null){
                                throw new NullPointerException("response JaxClientAuthentications is null");
                            }

                            long tempVerionId = versionId;
                            for(JaxClientAuthentication jaxClientAuthentication:jaxClientAuthentications) {
                                if (VersionUtil.checkVerion(jaxClientAuthentication.getVersionId(), versionId)) {
                                    jaxwayAuthenticationDataStore.updateAppAuthentications(jaxClientAuthentication);
                                    // update temp versionId
                                    if(jaxClientAuthentication.getVersionId()>tempVerionId){
                                        tempVerionId = jaxClientAuthentication.getVersionId();
                                    }
                                }
                            }
                            // update local versionId
                            versionId = tempVerionId;

                        }
                    } catch (Exception e) {
                        logger.log(Log.LogType.ERROR,e.getMessage());
                    }


                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }

    }

    public String selectPortalHost() {
        return loadBalanceService.selectServer(this.hosts);
    }

    private String generateUrl(String host) {
        return String.format(REQUEST_TEMPLATE, host, this.appId);
    }
    private String generateUrl(String host,long verionId) {
        return String.format(REQUEST_TEMPLATE, host, this.appId,String.valueOf(verionId));
    }

}
