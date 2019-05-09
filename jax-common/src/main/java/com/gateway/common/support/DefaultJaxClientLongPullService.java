package com.gateway.common.support;

import com.gateway.common.JaxwayClientAuthenticationDataStore;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.defaults.LocalJaxwayAuthenticationClientDataStore;
import com.gateway.common.support.http.HttpUtil;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.common.support.http.JaxHttpResponseWrapper;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

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

    private JaxwayClientAuthenticationDataStore jaxwayAuthenticationDataStore;

    private ExecutorService executorService;

    private RateLimiter longPollRateLimiter;

    private double longPullQPS = 0.001;

    private static String GROUP_NAME = "JaxWay";

    private static String JAX_PORTAL_HOST_PROPERTIES_NAME = "jaxway.host";

    private static String JAX_APP_ID_PROPERTIES_NAME = "jaxway.appid";

    private static String REQUEST_TEMPLATE = "%s/getAppInfo?appid=%s";

    private Environment env;

    private List<String> hosts;

    private String appId;

    private LoadBalanceService loadBalanceService;

    private int connectTimeout = 6;

    private int readTimeOut = 10;

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
        String requestUrl = generateUrl(selectPortalHost());
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted()) {
                    if (!longPollRateLimiter.tryAcquire(5, TimeUnit.MILLISECONDS)) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }

                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();
                    try {
                        ParameterizedTypeReference<JaxHttpResponseWrapper<JaxClientAuthentication>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<JaxClientAuthentication>>() {};
                        JaxHttpResponseWrapper<JaxClientAuthentication> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

                        if (responseWrapper.getCode() == 200) {
                            jaxwayAuthenticationDataStore.updateAppAuthentications(responseWrapper.getBody());
                        }
                    } catch (Exception e) {

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
}
