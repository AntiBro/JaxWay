package com.gateway.jaxway.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;

/**
 * @Author huaili
 * @Date 2019/5/5 20:12
 * @Description RouteService for test
 **/
@Service
public class RouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @PostConstruct
    public void init() throws URISyntaxException {
//        routeDefinitionWriter.save(Mono.just(RouteUtil.generatePathRouteDefition("http://127.0.0.1:8720","/sohu,/sohu/**"))).subscribe();
//        notifyChanged();
    }



    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
