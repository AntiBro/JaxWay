package com.gateway.jaxway.core.authority.server;

import com.gateway.jaxway.core.authority.JaxwayServerAuthenticationDataStore;
import com.gateway.jaxway.core.vo.JaxServerAuthentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Author huaili
 * @Date 2019/5/6 17:36
 * @Description LocalJaxwayAuthenticationServerDataStore
 **/
public class LocalJaxwayAuthenticationServerDataStore implements JaxwayServerAuthenticationDataStore {


    private static volatile Map<String,Set<String>> cachedAppAuthMap = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(JaxServerAuthentication jaxServerAuthentication) {
        // todo

    }

    @Override
    public String getRegxOfUri(String appId,String uri) {
        Set<String> uriRegxs = getAllAppAuthentications().get(appId);

        for(String uriRegx:uriRegxs){
            Pattern p = Pattern.compile(uriRegx);
            if(p.matcher(uri).matches()){
                return uriRegx;
            }
        }
        return null;
    }

    @Override
    public void updateAppAuthentications(Map<String, Set<String>> newAppAuthenticationMap) {
        cachedAppAuthMap.putAll(newAppAuthenticationMap);
    }

    @Override
    public Map<String, Set<String>> getAllAppAuthentications() {
        return cachedAppAuthMap;
    }

    @Override
    public boolean match(String appId, String uri) {
        if(StringUtils.isEmpty(appId)){
            return false;
        }
        if(CollectionUtils.isEmpty(getAllAppAuthentications().get(appId))){
            return false;
        }
        if(getRegxOfUri(appId,uri) != null)
            return true;
        return false;
    }
}
