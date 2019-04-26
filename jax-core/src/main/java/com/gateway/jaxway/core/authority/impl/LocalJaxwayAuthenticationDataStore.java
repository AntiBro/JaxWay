package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.core.common.JaxwayConstant;
import com.gateway.jaxway.core.utils.http.JaxAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huaili
 * @Date 2019/4/17 20:25
 * @Description LocalJaxwayAuthenticationDataStore
 **/
public class LocalJaxwayAuthenticationDataStore implements JaxwayAuthenticationDataStore {
    private Logger logger = LoggerFactory.getLogger(LocalJaxwayAuthenticationDataStore.class);

    private static JaxwayAuthenticationDataStore INSTANCE = new LocalJaxwayAuthenticationDataStore();

    private JaxwayTokenCoder jaxwayTokenCoder;

    private LocalJaxwayAuthenticationDataStore(){
        this(new Base64JaxwayTokenCoder());
    }

    private LocalJaxwayAuthenticationDataStore(JaxwayTokenCoder jaxwayTokenCoder){
        this.jaxwayTokenCoder = jaxwayTokenCoder;
    }

    public static JaxwayAuthenticationDataStore instance(){
        return INSTANCE;
    }

    private static volatile Map<String, Set<String>> whiteAppSets = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(Map<String, Set<String>> newAppAppAuthenticationMap) {
        whiteAppSets.putAll(newAppAppAuthenticationMap);
    }

    @Override
    public void updateAppAuthentications(JaxAuthentication jaxAuthentication) {
        String token = null;
        try {
            token = jaxwayTokenCoder.decode(jaxAuthentication.getToken());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("decode token error from portal,{}",e.getMessage());
            return ;
        }

        switch (jaxAuthentication.getOpType()){
            case ADD:
                Set<String> toAddTokenSet = whiteAppSets.get(jaxAuthentication.getUrl());
                if(CollectionUtils.isEmpty(toAddTokenSet)){
                    toAddTokenSet = new HashSet<>();
                    whiteAppSets.put(jaxAuthentication.getUrl(),toAddTokenSet);
                }
                toAddTokenSet.add(token);
                logger.trace("add info url={} token={} ",jaxAuthentication.getUrl(),token);
                break;
            case DELETE:
                Set<String> toDeleTokenSet = whiteAppSets.get(jaxAuthentication.getUrl());
                if(!CollectionUtils.isEmpty(toDeleTokenSet)){
                    toDeleTokenSet.remove(token);
                }
                logger.trace("dele info url={} token={} ",jaxAuthentication.getUrl(),token);
                break;
        }
    }

    @Override
    public Map<String, Set<String>> getAllAppAuthentications() {
        return whiteAppSets;
    }


}
