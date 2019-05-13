package com.gateway.jaxway.core.route.support;

import com.gateway.common.JaxRouteDefinitionRepository;
import com.gateway.common.JaxwayServerAuthenticationDataStore;
import com.gateway.common.JaxwayWhiteList;
import com.gateway.common.JaxwayWhiteListDataStore;
import com.gateway.common.beans.JaxRouteDefinition;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.common.defaults.LocalJaxwayAuthenticationServerDataStore;
import com.gateway.common.support.JaxwayThreadFactory;
import com.gateway.common.support.LoadBalanceService;
import com.gateway.common.support.http.HttpUtil;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.common.support.http.JaxHttpResponseWrapper;
import com.gateway.common.util.VersionUtil;
import com.gateway.jaxway.core.authority.server.LocalJaxwayWhiteList;
import com.gateway.jaxway.core.authority.server.LocalJaxwayWhiteListDataStore;
import com.gateway.jaxway.core.route.JaxRouteRefreshEvent;
import com.gateway.jaxway.core.route.JaxServerLongPullService;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.gateway.common.support.http.HttpUtil.SUCCESS_CODE;

/**
 * @Author huaili
 * @Date 2019/5/9 11:39
 * @Description DefaultJaxServerLongPullService
 **/
public class DefaultJaxServerLongPullService implements JaxServerLongPullService, ApplicationEventPublisherAware, DisposableBean {


    private Log logger = new DefaultLogImpl(getClass());

    private ExecutorService executorService;

    private ApplicationEventPublisher publisher;


    private RateLimiter longPollRateLimiterForWhiteList;

    private RateLimiter longPollRateLimiterForAppAuthority;

    private RateLimiter longPollRateLimiterForRouteDefinition;

    private double longPullQPS = 0.001;

    private static String GROUP_NAME = "JaxWay";

    private static String JAX_PORTAL_HOST_PROPERTIES_NAME = "jaxway.host";

    private static String JAX_WAY_APPID_PROPERTIES_NAME = "jaxway.server.id";

    private static String WHITE_LIST_REQUEST_TEMPLATE = "%s/server/getWhiteList?id=%s&versionId=%s";

    private static String APP_AUHTORITY_REQUEST_TEMPLATE = "%s/server/getAppInfo?id=%s&versionId=%s";

    private static String ROUTE_DEFINITION_REQUEST_TEMPLATE = "%s/server/getRouteInfo?id=%s&versionId=%s";

    private Environment env;

    private List<String> hosts;

    private String appId;

    private LoadBalanceService loadBalanceService;

    private int connectTimeout = 1500;

    private int readTimeOut = 800;

    private JaxwayWhiteList jaxwayWhiteList;

    private JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore;

    private JaxRouteDefinitionRepository jaxRouteDefinitionRepository;

    private JaxwayWhiteListDataStore jaxwayWhiteListDataStore;


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
        this.jaxwayWhiteListDataStore = new LocalJaxwayWhiteListDataStore();

        // begin long pull for server

        doLongPull(this.jaxwayWhiteList);
//
//        doLongPull(this.jaxwayServerAuthenticationDataStore);

        doLongPull(this.jaxRouteDefinitionRepository);

    }


    @Override
    public void doLongPull(JaxwayWhiteList jaxwayWhiteList) {
        HttpUtil httpUtil = HttpUtil.newInstance();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long versionId = -1;// pull the whole info
                while (!Thread.currentThread().isInterrupted()) {
                    String requestUrl = generateUrl(WHITE_LIST_REQUEST_TEMPLATE,selectPortalHost(),versionId);
                    if(!longPollRateLimiterForWhiteList.tryAcquire(5, TimeUnit.MILLISECONDS)){
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();

                    try{
                        ParameterizedTypeReference<JaxHttpResponseWrapper<JaxServerAuthentication>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<JaxServerAuthentication>>() {};
                        JaxHttpResponseWrapper<JaxServerAuthentication> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

                        if(responseWrapper.getCode() == SUCCESS_CODE){
                            JaxServerAuthentication jaxServerAuthentication = responseWrapper.getBody();
                            if(VersionUtil.checkVerion(jaxServerAuthentication.getVersionId(),versionId)) {
                                jaxwayWhiteListDataStore.updateWhiteList(jaxwayWhiteList, jaxServerAuthentication);
                                // update local versionId
                                versionId = jaxServerAuthentication.getVersionId();
                            }
                        }

                    }catch (Exception e){
                        logger.log(Log.LogType.ERROR,e);
                    }

                }
            }});
    }

    @Override
    public void doLongPull(JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore) {
        HttpUtil httpUtil = HttpUtil.newInstance();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long versionId = -1;// pull the whole info
                while (!Thread.currentThread().isInterrupted()) {
                    String requestUrl = generateUrl(APP_AUHTORITY_REQUEST_TEMPLATE,selectPortalHost(),versionId);
                    if(!longPollRateLimiterForAppAuthority.tryAcquire(5, TimeUnit.MILLISECONDS)){
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }

                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();

                    try{
                        ParameterizedTypeReference<JaxHttpResponseWrapper<JaxServerAuthentication>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<JaxServerAuthentication>>() {};
                        JaxHttpResponseWrapper<JaxServerAuthentication> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

                        if(responseWrapper.getCode() == SUCCESS_CODE){
                            JaxServerAuthentication jaxserverAuthentication = responseWrapper.getBody();
                            if(VersionUtil.checkVerion(jaxserverAuthentication.getVersionId(),versionId)) {
                                jaxwayServerAuthenticationDataStore.updateAppAuthentications(responseWrapper.getBody());
                                // update local versionId
                                versionId = jaxserverAuthentication.getVersionId();
                            }
                        }

                    }catch (Exception e){
                        logger.log(Log.LogType.ERROR,e);

                    }
                }
            }});
    }


    @Override
    public void doLongPull(JaxRouteDefinitionRepository jaxRouteDefinitionRepository) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long versionId = -1;// pull the whole info
                while (!Thread.currentThread().isInterrupted()) {
                    String requestUrl = generateUrl(ROUTE_DEFINITION_REQUEST_TEMPLATE,selectPortalHost(),versionId);
                    if(!longPollRateLimiterForRouteDefinition.tryAcquire(5, TimeUnit.MILLISECONDS)){
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }

                    JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl(requestUrl).connectionTimeOut(connectTimeout).readTimeOut(readTimeOut).build();

                    try{
                        List<JaxRouteDefinition> jaxRouteDefinitions = jaxRouteDefinitionRepository.getJaxRouteDefinitions(jaxHttpRequest);
                        if(!CollectionUtils.isEmpty(jaxRouteDefinitions)){
                            for(JaxRouteDefinition jaxRouteDefinition :jaxRouteDefinitions){
                                if(VersionUtil.checkVerion(jaxRouteDefinition.getVersionId(),versionId)){
                                    // publish change event
                                    notifyChanged(jaxRouteDefinition);
                                }
                            }
                            // update local versionId to the Max versionId
                            versionId = jaxRouteDefinitions.get(jaxRouteDefinitions.size()-1).getVersionId();
                        }
                    }catch(Exception e){
                        logger.log(Log.LogType.ERROR,e);
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
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    public String selectPortalHost() {
        return loadBalanceService.selectServer(this.hosts);
    }


    private String generateUrl(String template,String host,long verionId) {
        return String.format(template, host, this.appId,String.valueOf(verionId));
    }

    private void notifyChanged(JaxRouteDefinition jaxRouteDefinition) {
        this.publisher.publishEvent(new JaxRouteRefreshEvent(this,jaxRouteDefinition));

    }

}
