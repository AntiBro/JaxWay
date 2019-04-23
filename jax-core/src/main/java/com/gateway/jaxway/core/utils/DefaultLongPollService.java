package com.gateway.jaxway.core.utils;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.impl.LocalJaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.utils.http.HttpUtil;
import com.gateway.jaxway.core.utils.http.JaxAuthentication;
import com.gateway.jaxway.core.utils.http.JaxHttpRequest;
import com.gateway.jaxway.core.utils.http.JaxHttpResponseWrapper;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author huaili
 * @Date 2019/4/23 17:58
 * @Description DefaultLongPollService
 **/
public class DefaultLongPollService implements LongPollService, DisposableBean{

    private JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore;

    private ExecutorService executorService;

    private RateLimiter longPollRateLimiter;

    private long longPullQPS = 2;

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

    public DefaultLongPollService(Environment env,LoadBalanceService loadBalanceService){
        this.loadBalanceService = loadBalanceService;
        this.env = env;
        this.hosts = Arrays.asList(this.env.getProperty(JAX_PORTAL_HOST_PROPERTIES_NAME).split(","));
        this.appId = this.env.getProperty(JAX_APP_ID_PROPERTIES_NAME);
        this.executorService = Executors.newSingleThreadExecutor(JaxwayThreadFactory.create(GROUP_NAME,true));
        this.longPollRateLimiter = RateLimiter.create(longPullQPS);
        this.jaxwayAuthenticationDataStore = LocalJaxwayAuthenticationDataStore.instance();
    }

    public DefaultLongPollService(Environment env){
         this(env, LoadBalanceService.RandomLoadBalanceService);
    }


    @Override
    public void doLongPoll(JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore) {
        HttpUtil httpUtil = HttpUtil.newInstance();
        String requestUrl = generateUrl(selectPortalHost());
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                while(!Thread.currentThread().isInterrupted()){
                    if(!longPollRateLimiter.tryAcquire(5, TimeUnit.MILLISECONDS)){
                        try {
                            TimeUnit.MILLISECONDS.sleep(5);
                        } catch (InterruptedException e) {
                        }

                        JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();
                        JaxHttpResponseWrapper<JaxAuthentication> responseWrapper = httpUtil.doGet(jaxHttpRequest);

                        if(responseWrapper.getCode() == 200){
                            jaxwayAuthenticationDataStore.updateAppAuthentications(responseWrapper.getBody());
                        }

                    }
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        if(executorService!=null && !executorService.isShutdown()){
            executorService.shutdown();
        }

    }

    public String selectPortalHost(){
        return loadBalanceService.selectServer(this.hosts);
    }

    private String generateUrl(String host){
        return String.format(REQUEST_TEMPLATE,host,this.appId);
    }
}
