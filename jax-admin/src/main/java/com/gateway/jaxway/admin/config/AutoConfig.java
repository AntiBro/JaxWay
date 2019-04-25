package com.gateway.jaxway.admin.config;

import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.core.authority.impl.Base64JaxwayTokenCoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huaili
 * @Date 2019/4/24 20:47
 * @Description TODO
 **/
@Configuration
public class AutoConfig {

    @Bean
    public JaxwayTokenCoder jaxwayTokenCoder(){
        return new Base64JaxwayTokenCoder();
    }
}
