package com.gateway.jaxway.server.validator;

import com.alibaba.fastjson.JSON;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import com.gateway.jaxway.support.beans.JaxRouteDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huaili
 * @Date 2019/5/24 14:29
 * @Description DefaultJaxServerRouteDefinitionValidator
 **/
public class DefaultJaxServerRouteDefinitionValidator implements JaxServerRouteDefinitionValidator {

    private Log logger = new DefaultLogImpl(DefaultJaxServerRouteDefinitionValidator.class);

    private static volatile Map<String,ConfigurableApplicationContext> locaJaxwayServerMap = new ConcurrentHashMap<>();

    private Environment env;
    public DefaultJaxServerRouteDefinitionValidator(Environment env){
        this.env = env;
    }

    @Override
    public void addJaxServer(String JaxwayServerId) {
        JaxServerValidatorApplication.changerPortModeToInnerGateway();
        JaxServerValidatorApplication.setSpringBootApplicationName(JaxwayServerId);
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(JaxServerValidatorApplication.class).profiles("dev").
                web(WebApplicationType.REACTIVE).
                build().
                run();
        locaJaxwayServerMap.put(JaxwayServerId,applicationContext);

    }

    @Async
    @Override
    public CompletableFuture<Boolean> verifyRouteDefintion(String jaxwayServerId, JaxRouteDefinition jaxRouteDefinition) throws InterruptedException {
        ConfigurableApplicationContext context;
        synchronized (locaJaxwayServerMap) {
            context = locaJaxwayServerMap.get(jaxwayServerId);
            if (context == null || !context.isActive()) {
//                CountDownLatch countDownLatch = new CountDownLatch(1);
//                // must start a new Thread to start the verify Jaxway Server
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        addJaxServer(jaxwayServerId);
//                        countDownLatch.countDown();
//                    }
//                }).start();
//                countDownLatch.await();
                context = locaJaxwayServerMap.get(jaxwayServerId);
            }

        }

        RouteDefinitionWriter routeDefinitionWriter = context.getBean(RouteDefinitionWriter.class);
        RouteDefinition routeDefinition = convertJaxRouteDefinition(jaxRouteDefinition);
        try {
            switch (jaxRouteDefinition.getOpType()) {
                case ADD_ROUTE:
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                    logger.log("verify route, add route definition route={}",jaxRouteDefinition);
                    notifyChanged(context);
                    break;
                case DELETE_ROUTE:
                    routeDefinitionWriter.delete(Mono.just(jaxRouteDefinition.getId())).subscribe();
                    logger.log("verify route,delete route definition routeId={} route={}",jaxRouteDefinition.getId(),jaxRouteDefinition);
                    notifyChanged(context);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.log(Log.LogType.ERROR,"verify route, jaxway server={} get wrong routedefinition ={}",jaxwayServerId, JSON.toJSONString(routeDefinition));
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public void destroy() throws Exception {
        for(ConfigurableApplicationContext context:locaJaxwayServerMap.values()){
            context.stop();
        }
    }

    private void notifyChanged(ConfigurableApplicationContext publisher){
       publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private RouteDefinition convertJaxRouteDefinition(JaxRouteDefinition jaxRouteDefinition){
        RouteDefinition routeDefinition = new RouteDefinition();

        List<PredicateDefinition> predicateDefinitionList = new ArrayList<>();
        List<FilterDefinition> filterDefinitionList = new ArrayList<>();
        for(com.gateway.jaxway.support.beans.PredicateDefinition predicateDefinition:jaxRouteDefinition.getPredicates()){
            PredicateDefinition predicateDefinition1 = new PredicateDefinition();
            BeanUtils.copyProperties(predicateDefinition,predicateDefinition1);
            predicateDefinitionList.add(predicateDefinition1);

        }
        for(com.gateway.jaxway.support.beans.FilterDefinition filterDefinition:jaxRouteDefinition.getFilters()){
            FilterDefinition filterDefinition1 = new FilterDefinition();
            BeanUtils.copyProperties(filterDefinition,filterDefinition1);
            filterDefinitionList.add(filterDefinition1);

        }
        // ignore list properties
        BeanUtils.copyProperties(jaxRouteDefinition,routeDefinition,"predicates","filters");
        routeDefinition.setPredicates(predicateDefinitionList);
        routeDefinition.setFilters(filterDefinitionList);
        return routeDefinition;
    }

}
