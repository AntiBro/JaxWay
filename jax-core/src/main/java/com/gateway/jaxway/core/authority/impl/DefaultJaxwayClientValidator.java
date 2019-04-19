package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayClientValidator;
import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.log.Log;

/**
 * @Author huaili
 * @Date 2019/4/17 17:25
 * @Description DefaultJaxwayClientValidator
 **/
public class DefaultJaxwayClientValidator implements JaxwayClientValidator {
    private Log log;

    private JaxwayTokenCoder jaxwayTokenCoder;

    private JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore;

    public DefaultJaxwayClientValidator(JaxwayTokenCoder jaxwayTokenCoder, Log log){
        this.jaxwayTokenCoder = jaxwayTokenCoder;
        this.log = log;
    }

    @Override
    public boolean validate(String requestUrl,String token) {
        log.log("origin token= "+token);
        token = jaxwayTokenCoder.decode(token);
        log.log("decoder token= "+token);W
        return jaxwayAuthenticationDataStore.contains(requestUrl,token);
    }
}
