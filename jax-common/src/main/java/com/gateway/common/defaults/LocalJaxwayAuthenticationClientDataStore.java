package com.gateway.common.defaults;

import com.gateway.common.JaxwayClientAuthenticationDataStore;
import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.JaxClientAuthentication;
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
 * @Description LocalJaxwayAuthenticationClientDataStore
 **/
public class LocalJaxwayAuthenticationClientDataStore implements JaxwayClientAuthenticationDataStore {
    private Logger logger = LoggerFactory.getLogger(LocalJaxwayAuthenticationClientDataStore.class);

    private static JaxwayClientAuthenticationDataStore INSTANCE = new LocalJaxwayAuthenticationClientDataStore();

    private JaxwayCoder jaxwayCoder;

    private LocalJaxwayAuthenticationClientDataStore(){
        this(new Base64JaxwayCoder());
    }

    private LocalJaxwayAuthenticationClientDataStore(JaxwayCoder jaxwayCoder){
        this.jaxwayCoder = jaxwayCoder;
    }

    public static JaxwayClientAuthenticationDataStore instance(){
        return INSTANCE;
    }

    private static volatile Map<String, Set<String>> cachedWhiteAppPathPatternSets = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(Map<String, Set<String>> newAppAppAuthenticationMap) {
        cachedWhiteAppPathPatternSets.putAll(newAppAppAuthenticationMap);
    }

    @Override
    public void updateAppAuthentications(JaxClientAuthentication jaxAuthentication) {
        String token = null;
        String appId = null;
        try {
            token = jaxwayCoder.decode(jaxAuthentication.getPathPattern());
            appId = jaxwayCoder.decode(jaxAuthentication.getAppId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("decode token error from portal,{}",e.getMessage());
            return ;
        }

        switch (jaxAuthentication.getOpType()){
            case ADD_APP:
                Set<String> toAddTokenSet = cachedWhiteAppPathPatternSets.get(appId);
                if(CollectionUtils.isEmpty(toAddTokenSet)){
                    toAddTokenSet = new HashSet<>();
                    cachedWhiteAppPathPatternSets.put(appId,toAddTokenSet);
                }
                toAddTokenSet.add(token);
                logger.info("add info appId={} pathPattern={} ",appId,token);
                break;
            case DELETE_APP:
                Set<String> toDeleTokenSet = cachedWhiteAppPathPatternSets.get(appId);
                if(!CollectionUtils.isEmpty(toDeleTokenSet)){
                    toDeleTokenSet.remove(token);
                }
                logger.info("dele info appId={} pathPattern={} ",appId,token);
                break;
        }
    }

    @Override
    public Map<String, Set<String>> getAllAppAuthentications() {
        return cachedWhiteAppPathPatternSets;
    }


}
