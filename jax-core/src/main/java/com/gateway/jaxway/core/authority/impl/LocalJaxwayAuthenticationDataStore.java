package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huaili
 * @Date 2019/4/17 20:25
 * @Description LocalJaxwayAuthenticationDataStore
 **/
public class LocalJaxwayAuthenticationDataStore implements JaxwayAuthenticationDataStore {

    private static JaxwayAuthenticationDataStore INSTANCE = new LocalJaxwayAuthenticationDataStore();

    private LocalJaxwayAuthenticationDataStore(){ }

    public static JaxwayAuthenticationDataStore instance(){
        return INSTANCE;
    }

    private static Map<String, String> whiteAppSets = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(Map<String, String> newAppAppAuthenticationMap) {
        whiteAppSets.putAll(newAppAppAuthenticationMap);
    }

    @Override
    public Map<String, String> getAllAppAuthentications() {
        return whiteAppSets;
    }
}
