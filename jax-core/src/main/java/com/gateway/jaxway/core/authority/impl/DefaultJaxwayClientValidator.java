package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayClientValidator;
import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.core.authority.bean.JaxRequest;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;

/**
 * @Author huaili
 * @Date 2019/4/17 17:25
 * @Description DefaultJaxwayClientValidator
 **/
public class DefaultJaxwayClientValidator implements JaxwayClientValidator {
    private Log log;

    private JaxwayTokenCoder jaxwayTokenCoder;

    private JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore;

    public DefaultJaxwayClientValidator(JaxwayTokenCoder jaxwayTokenCoder,JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore){
        this.jaxwayTokenCoder = jaxwayTokenCoder;
        this.jaxwayAuthenticationDataStore = jaxwayAuthenticationDataStore;
        this.log = new DefaultLogImpl();
    }

    public DefaultJaxwayClientValidator(JaxwayTokenCoder jaxwayTokenCoder, Log log){
        this.jaxwayTokenCoder = jaxwayTokenCoder;
        this.log = log;
    }

    @Override
    public boolean validate(JaxRequest jaxRequest) {
        log.log("origin token= "+jaxRequest.getToken());
        jaxRequest.setToken(jaxwayTokenCoder.decode(jaxRequest.getToken()));
        log.log("decoder token= "+jaxRequest.getToken());
        return jaxwayAuthenticationDataStore.contains(jaxRequest.getUrl(),jaxRequest.getToken());
    }
}
