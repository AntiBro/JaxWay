package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayClientValidator;
import com.gateway.jaxway.core.authority.JaxwayCoder;
import com.gateway.jaxway.core.authority.bean.JaxRequest;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Author huaili
 * @Date 2019/4/17 17:25
 * @Description DefaultJaxwayClientValidator
 **/
public class DefaultJaxwayClientValidator implements JaxwayClientValidator {
    private Log log;

    private JaxwayCoder jaxwayCoder;

    private JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore;

    public DefaultJaxwayClientValidator(JaxwayCoder jaxwayCoder, JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore){
        this.jaxwayCoder = jaxwayCoder;
        this.jaxwayAuthenticationDataStore = jaxwayAuthenticationDataStore;
        this.log = new DefaultLogImpl(DefaultJaxwayClientValidator.class);
    }

    public DefaultJaxwayClientValidator(JaxwayCoder jaxwayCoder, Log log){
        this.jaxwayCoder = jaxwayCoder;
        this.log = log;
    }

    @Override
    public boolean validate(JaxRequest jaxRequest) throws UnsupportedEncodingException {
        if(StringUtils.isEmpty(jaxRequest.getToken())){
            return false;
        }
        log.log(Log.LogType.DEBUG,"origin token= "+jaxRequest.getToken());
        jaxRequest.setToken(jaxwayCoder.decode(jaxRequest.getToken()));
        log.log(Log.LogType.DEBUG,"decoder token= "+jaxRequest.getToken());
        return jaxwayAuthenticationDataStore.match(jaxRequest.getUrl(),jaxRequest.getToken());
    }
}
