package com.gateway.jaxway.admin.config;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.defaults.Base64JaxwayCoder;
import com.gateway.jaxway.server.validator.DefaultJaxServerRouteDefinitionValidator;
import com.gateway.jaxway.server.validator.JaxServerRouteDefinitionValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author huaili
 * @Date 2019/4/24 20:47
 * @Description config
 **/
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AutoConfig {

    @Bean
    public JaxwayCoder jaxwayTokenCoder(){
        return new Base64JaxwayCoder();
    }

    @Bean
    public JaxServerRouteDefinitionValidator jaxServerRouteDefinitionValidator(Environment env){
        return  new DefaultJaxServerRouteDefinitionValidator(env);
    }
}
