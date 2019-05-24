package com.gateway.jaxway.core.route;

import com.gateway.common.beans.JaxRouteDefinition;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import reactor.core.publisher.Mono;

/**
 * @Author huaili
 * @Date 2019/5/9 16:21
 * @Description JaxRouteRefreshListener
 **/
public class JaxRouteRefreshListener implements ApplicationListener<JaxRouteRefreshEvent>, ApplicationEventPublisherAware {
    private Log logger = new DefaultLogImpl(getClass());

    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    public RouteDefinitionWriter getRouteDefinitionWriter() {
        return routeDefinitionWriter;
    }

    public void setRouteDefinitionWriter(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    public JaxRouteRefreshListener(){

    }

    public JaxRouteRefreshListener(RouteDefinitionWriter routeDefinitionWriter){
        this.routeDefinitionWriter = routeDefinitionWriter;
    }


    @Override
    public void onApplicationEvent(JaxRouteRefreshEvent jaxRouteRefreshEvent) {
        JaxRouteDefinition jaxRouteDefinition = jaxRouteRefreshEvent.getJaxRouteDefinition();

        //  save or delete RouteDefinitionWriter must end with subscribe(),otherwise routedifinition will not take effect
        switch (jaxRouteDefinition.getOpType()){
            case ADD_ROUTE:
                routeDefinitionWriter.save(Mono.just(jaxRouteDefinition)).subscribe();
                logger.log("add route definition route={}",jaxRouteDefinition);
                notifyChanged();
                break;
            case DELETE_ROUTE:
                routeDefinitionWriter.delete(Mono.just(jaxRouteDefinition.getId())).subscribe();
                logger.log("delete route definition routeId={} route={}",jaxRouteDefinition.getId(),jaxRouteDefinition);
                notifyChanged();
                break;
            case UPDATE_ROUTE:
                routeDefinitionWriter.delete(Mono.just(jaxRouteDefinition.getId())).subscribe();
                routeDefinitionWriter.save(Mono.just(jaxRouteDefinition)).subscribe();
                logger.log("update route definition route={}",jaxRouteDefinition);
                notifyChanged();
                break;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
