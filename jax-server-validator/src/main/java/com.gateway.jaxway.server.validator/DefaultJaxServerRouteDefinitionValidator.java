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
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author huaili
 * @Date 2019/5/24 14:29
 * @Description DefaultJaxServerRouteDefinitionValidator
 **/
public class DefaultJaxServerRouteDefinitionValidator implements JaxServerRouteDefinitionValidator {

    private Log logger = new DefaultLogImpl(DefaultJaxServerRouteDefinitionValidator.class);

    private AtomicInteger port = new AtomicInteger(9710);

    private Map<String,ConfigurableApplicationContext> locaJaxwayServerMap = new ConcurrentHashMap<>();

    private Environment env;
    public DefaultJaxServerRouteDefinitionValidator(Environment env){
        this.env = env;
    }

    @Override
    public void addJaxServer(String JaxwayServerId) {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("spring.application.name",JaxwayServerId);
        defaults.put("server.port",port.incrementAndGet());

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(JaxServerValidatorApplication.class).profiles("dev").
                web(WebApplicationType.REACTIVE).
                properties("spring.application.name="+JaxwayServerId).
                properties("server.port="+port.incrementAndGet()).
                properties(defaults).
                build().
                run("-Dserver.port="+port.incrementAndGet());
        locaJaxwayServerMap.put(JaxwayServerId,applicationContext);

    }

    @Override
    public boolean verifyRouteDenifion(String jaxwayServerId, JaxRouteDefinition jaxRouteDefinition) {
        ConfigurableApplicationContext context = locaJaxwayServerMap.get(jaxwayServerId);
        RouteDefinitionWriter routeDefinitionWriter = context.getBean(RouteDefinitionWriter.class);
     //   ApplicationEventPublisher publisher = context.getBean(ApplicationEventPublisher.class);
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
        BeanUtils.copyProperties(jaxRouteDefinition,routeDefinition,"predicates","filters");
        routeDefinition.setPredicates(predicateDefinitionList);
        routeDefinition.setFilters(filterDefinitionList);

        try {
            switch (jaxRouteDefinition.getOpType()) {
                case ADD_ROUTE:
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                    logger.log("add route definition route={}",jaxRouteDefinition);
                    notifyChanged(context);
                    break;
                case DELETE_ROUTE:
                    routeDefinitionWriter.delete(Mono.just(jaxRouteDefinition.getId())).subscribe();
                    logger.log("delete route definition routeId={} route={}",jaxRouteDefinition.getId(),jaxRouteDefinition);
                    notifyChanged(context);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.log(Log.LogType.ERROR," jaxway server={} get wrong routedefinition ={}",jaxwayServerId, JSON.toJSONString(routeDefinition));
            return false;
        }
        return true;
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

    private int getAvailablePort(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
            return port;
        } catch (IOException e) {
            return getAvailablePort(port + 1);
        }
    }
}
