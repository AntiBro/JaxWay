package com.gateway.jaxway.starter;

import com.gateway.jaxway.core.authority.JaxClientServletFilter;
import com.gateway.jaxway.core.authority.JaxClientWebFluxFilter;
import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.impl.LocalJaxwayAuthenticationDataStore;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.LogProxyService;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebFilter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huaili
 * @Date 2019/4/22 10:51
 * @Description AutoJaxwayConfig for client
 **/
@Configuration
@ConditionalOnProperty(name = "spring.jaxway.filter.enable", havingValue = "true")
public class AutoJaxwayConfig {

    private Log log = new DefaultLogImpl(AutoJaxwayConfig.class);

    @Bean
    @Primary
    public Log JaxWayProxyLog(){
        return new LogProxyService();
    }


    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public FilterRegistrationBean someFilterRegistration() {
        log.log("loading for JaxClientServletFilter");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new JaxClientServletFilter(new DefaultLogImpl(JaxClientServletFilter.class)));
        registration.addUrlPatterns("/*");
        registration.setName("JaxClientServletFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }


    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Order(Integer.MIN_VALUE)
    public WebFilter JaxWayWebFilter(){
        log.log("loading for JaxClientWebFluxFilter");
        return new JaxClientWebFluxFilter(new DefaultLogImpl(JaxClientWebFluxFilter.class));
    }

    @PostConstruct
    public void setWhiteMaps(){
        log.log("loading for test setWhiteMaps,白名单测试");
        JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore = LocalJaxwayAuthenticationDataStore.instance();
        Map<String,String> map = new HashMap<String,String>();
        map.put("/testflux","123");
        jaxwayAuthenticationDataStore.updateAppAuthentications(map);
    }

}
