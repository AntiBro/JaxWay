package com.gateway.jaxway.core.authority.client;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayClientAuthenticationDataStore;
import com.gateway.jaxway.core.authority.JaxwayCoder;
import com.gateway.jaxway.core.authority.impl.Base64JaxwayCoder;
import com.gateway.jaxway.core.vo.JaxAuthentication;
import com.gateway.jaxway.core.vo.JaxClientAuthentication;
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

    private static volatile Map<String, Set<String>> whiteAppSets = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(Map<String, Set<String>> newAppAppAuthenticationMap) {
        whiteAppSets.putAll(newAppAppAuthenticationMap);
    }

    @Override
    public void updateAppAuthentications(JaxClientAuthentication jaxAuthentication) {
        String token = null;
        try {
            token = jaxwayCoder.decode(jaxAuthentication.getToken());
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
