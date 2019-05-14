package com.gateway.jaxway.server.config;

import com.gateway.common.JaxwayConstant;
import com.gateway.common.support.LoadBalanceService;
import com.gateway.jaxway.core.authority.server.JaxwayServerWebFluxFilter;
import com.gateway.jaxway.core.route.JaxRouteRefreshListener;
import com.gateway.jaxway.core.route.JaxServerLongPullService;
import com.gateway.jaxway.core.route.support.DefaultJaxServerLongPullService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.server.WebFilter;

import static com.gateway.common.JaxwayConstant.JAX_WAY_FILTER_ENABLE_NAME;

/**
 * @Author huaili
 * @Date 2019/5/6 18:43
 * @Description JaxServerConfig
 **/
@Configuration
public class JaxServerConfig {

    @Bean
    @Order(Integer.MIN_VALUE)
    @ConditionalOnProperty(name = JAX_WAY_FILTER_ENABLE_NAME, havingValue = "true")
    public WebFilter serverWebFilter(){
        return new JaxwayServerWebFluxFilter();
    }

    @Bean
    public JaxServerLongPullService longPullService(Environment env){
        return new DefaultJaxServerLongPullService(env, LoadBalanceService.RandomLoadBalanceService);
    }


    @Bean
    public JaxRouteRefreshListener jaxRouteRefreshListener(RouteDefinitionWriter routeDefinitionWriter){
       return new JaxRouteRefreshListener(routeDefinitionWriter);
    }
}
