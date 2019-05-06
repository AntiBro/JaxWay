package com.gateway.jaxway.server.config;

import com.gateway.jaxway.core.authority.server.JaxwayServerWebFluxFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebFilter;

/**
 * @Author huaili
 * @Date 2019/5/6 18:43
 * @Description JaxServerConfig
 **/
@Configuration
public class JaxServerConfig {

    @Bean
    @Order(Integer.MIN_VALUE)
    @ConditionalOnProperty(name = "spring.jaxway.filter.enable", havingValue = "true")
    WebFilter serverWebFilter(){
        return new JaxwayServerWebFluxFilter();
    }
}
