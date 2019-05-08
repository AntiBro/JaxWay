package com.gateway.jaxway.admin.config;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.defaults.Base64JaxwayCoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huaili
 * @Date 2019/4/24 20:47
 * @Description config
 **/
@Configuration
public class AutoConfig {

    @Bean
    public JaxwayCoder jaxwayTokenCoder(){
        return new Base64JaxwayCoder();
    }
}
