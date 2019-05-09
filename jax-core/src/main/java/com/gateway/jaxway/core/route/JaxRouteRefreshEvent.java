package com.gateway.jaxway.core.route;

import com.gateway.common.beans.JaxRouteDefinition;
import org.springframework.context.ApplicationEvent;

/**
 * @Author huaili
 * @Date 2019/5/9 16:18
 * @Description JaxRouteRefreshEvent
 **/
public class JaxRouteRefreshEvent extends ApplicationEvent {

    private JaxRouteDefinition jaxRouteDefinition;

    public JaxRouteRefreshEvent(Object source) {
        super(source);
    }

    public JaxRouteRefreshEvent(Object source, JaxRouteDefinition jaxRouteDefinition){
        this(source);
        this.jaxRouteDefinition = jaxRouteDefinition;
    }

    public JaxRouteDefinition getJaxRouteDefinition() {
        return jaxRouteDefinition;
    }

    public void setJaxRouteDefinition(JaxRouteDefinition jaxRouteDefinition) {
        this.jaxRouteDefinition = jaxRouteDefinition;
    }
}
