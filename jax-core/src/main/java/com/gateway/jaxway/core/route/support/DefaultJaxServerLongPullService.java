package com.gateway.jaxway.core.route.support;

import com.gateway.common.JaxRouteDefinitionRepository;
import com.gateway.common.JaxwayServerAuthenticationDataStore;
import com.gateway.common.JaxwayWhiteList;
import com.gateway.common.defaults.LocalJaxwayAuthenticationServerDataStore;
import com.gateway.common.support.JaxwayThreadFactory;
import com.gateway.common.support.LoadBalanceService;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.jaxway.core.authority.server.LocalJaxwayWhiteList;
import com.gateway.jaxway.core.route.JaxServerLongPullService;
import com.google.common.util.concurrent.RateLimiter;
import io.netty.util.NetUtil;
import org.bouncycastle.util.IPAddress;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import sun.net.util.IPAddressUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author huaili
 * @Date 2019/5/9 11:39
 * @Description DefaultJaxServerLongPullService
 **/
public class DefaultJaxServerLongPullService implements JaxServerLongPullService, ApplicationContextAware, DisposableBean {


    private ExecutorService executorService;


    private ApplicationContext applicationContext;

    private RateLimiter longPollRateLimiterForWhiteList;

    private RateLimiter longPollRateLimiterForAppAuthority;

    private RateLimiter longPollRateLimiterForRouteDefinition;

    private double longPullQPS = 0.001;

    private static String GROUP_NAME = "JaxWay";

    private static String JAX_PORTAL_HOST_PROPERTIES_NAME = "jaxway.host";

    private static String JAX_WAY_APPID_PROPERTIES_NAME = "jaxway.server.id";

    private static String WHITE_LIST_REQUEST_TEMPLATE = "%s/server/getWhiteList?id=%s";

    private static String APP_AUHTORITY_REQUEST_TEMPLATE = "%s/server/getAppInfo?id=%s";

    private static String ROUTE_DEFINITION_REQUEST_TEMPLATE = "%s/server/getRouteInfo?id=%s";

    private Environment env;

    private List<String> hosts;

    private String appId;

    private LoadBalanceService loadBalanceService;

    private int connectTimeout = 6;

    private int readTimeOut = 10;

    private JaxwayWhiteList jaxwayWhiteList;

    private JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore;

    private JaxRouteDefinitionRepository jaxRouteDefinitionRepository;

    public DefaultJaxServerLongPullService(Environment env, LoadBalanceService loadBalanceService){
        Assert.notNull(env.getProperty(JAX_PORTAL_HOST_PROPERTIES_NAME), "jaxway.host for portal admin has not set ");
        Assert.notNull(env.getProperty(JAX_WAY_APPID_PROPERTIES_NAME), "jaxway.server.id for portal admin has not set ");


        this.env = env;
        this.loadBalanceService = loadBalanceService;
        this.hosts = Arrays.asList(this.env.getProperty(JAX_PORTAL_HOST_PROPERTIES_NAME).split(","));
        this.appId = this.env.getProperty(JAX_WAY_APPID_PROPERTIES_NAME);
        executorService = Executors.newFixedThreadPool(3,JaxwayThreadFactory.create(GROUP_NAME, true));

        this.longPollRateLimiterForWhiteList = RateLimiter.create(longPullQPS);
        this.longPollRateLimiterForAppAuthority = RateLimiter.create(longPullQPS);
        this.longPollRateLimiterForRouteDefinition = RateLimiter.create(longPullQPS);

        this.jaxwayWhiteList = LocalJaxwayWhiteList.create();
        this.jaxwayServerAuthenticationDataStore = LocalJaxwayAuthenticationServerDataStore.create();

        this.jaxRouteDefinitionRepository = new DefaultJaxRouteDefinitionRepository();
        // do long pull
        doLongPull(this.jaxwayWhiteList);

        doLongPull(this.jaxwayServerAuthenticationDataStore);

        doLongPull(this.jaxRouteDefinitionRepository);

    }


    @Override
    public void doLongPull(JaxwayWhiteList jaxwayWhiteList) {

    }

    @Override
    public void doLongPull(JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore) {

    }

    @Override
    public void doLongPull(JaxRouteDefinitionRepository jaxRouteDefinitionRepository) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    String requestUrl = generateUrl(ROUTE_DEFINITION_REQUEST_TEMPLATE,selectPortalHost());
                    if(!longPollRateLimiterForRouteDefinition.tryAcquire(5, TimeUnit.MILLISECONDS)){
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }

                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();

                    try{

                    }catch(Exception e){

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public String selectPortalHost() {
        return loadBalanceService.selectServer(this.hosts);
    }

    private String generateUrl(String template,String host) {
        return String.format(template, host,this.appId);
    }

    private String getLocalServerIP(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }
}
