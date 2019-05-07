package com.gateway.jaxway.core.authority.server;

import com.gateway.jaxway.core.authority.JaxwayServerAuthenticationDataStore;
import com.gateway.jaxway.core.vo.JaxServerAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.server.PathContainer.parsePath;

/**
 * @Author huaili
 * @Date 2019/5/6 17:36
 * @Description LocalJaxwayAuthenticationServerDataStore
 **/
public class LocalJaxwayAuthenticationServerDataStore implements JaxwayServerAuthenticationDataStore {

    private static JaxwayServerAuthenticationDataStore INSTANCE = new LocalJaxwayAuthenticationServerDataStore();
    private Logger logger = LoggerFactory.getLogger(LocalJaxwayAuthenticationServerDataStore.class);

    private PathPatternParser pathPatternParser = new PathPatternParser();

    private LocalJaxwayAuthenticationServerDataStore(){

    }

    public static JaxwayServerAuthenticationDataStore create(){
        return INSTANCE;
    }


    //private static volatile Map<String,Set<String>> cachedAppAuthMap = new ConcurrentHashMap<>();

    private static volatile Map<String,Set<PathPattern>> cachedAppAuthMapPathPattern = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(JaxServerAuthentication jaxServerAuthentication) {
        // todo
        switch (jaxServerAuthentication.getOpType()){
            case DELETE:
                if(!CollectionUtils.isEmpty(cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId()))){
//                    Set<String> uriRegx = cachedAppAuthMap.get(jaxServerAuthentication.getAppId());
//                    uriRegx.removeAll(jaxServerAuthentication.getUriRegxSet());
                    logger.trace("jax-way server delete app authentication info={}",jaxServerAuthentication.getUriRegxSet());

                    Set<PathPattern> uriRegxPathPattern = cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId());
                    uriRegxPathPattern.removeAll(parseUriSet(jaxServerAuthentication.getUriRegxSet()));
                }

                break;
            case ADD:
                Set<PathPattern> uriRegx;
                if(CollectionUtils.isEmpty(cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId()))){
                    uriRegx = new HashSet<>();
                    cachedAppAuthMapPathPattern.put(jaxServerAuthentication.getAppId(),uriRegx);
                }else{
                    uriRegx = cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId());
                }
                if( !CollectionUtils.isEmpty(jaxServerAuthentication.getUriRegxSet())) {
                    uriRegx.addAll(parseUriSet(jaxServerAuthentication.getUriRegxSet()));
                    logger.trace("jax-way server add app authentication info={}",jaxServerAuthentication.getUriRegxSet());
                }

                break;
            case NONE:
                logger.trace("jax-way server app authentication OpType=NONE");
                break;
            default:
                logger.error("jax-way server inlegal app authentication OpType={}",jaxServerAuthentication.getOpType());
                break;
        }

    }

    @Override
    public String getRegxOfUri(String appId,String uri) {
        Set<PathPattern> uriRegxs = cachedAppAuthMapPathPattern.get(appId);
        Optional<PathPattern> optionalPathPattern = uriRegxs.stream().filter(pattern -> pattern.matches(parsePath(uri))).findFirst();
        if(optionalPathPattern.isPresent()){
            return optionalPathPattern.get().getPatternString();
        }
        return null;
    }

    @Override
    public void updateAppAuthentications(Map<String, Set<String>> newAppAuthenticationMap) {
        for(Map.Entry<String,Set<String>> e:newAppAuthenticationMap.entrySet()){
            String appId = e.getKey();
            Set<String> uriRegx = e.getValue();
            Set<PathPattern> uriRegxPathPattern = parseUriSet(uriRegx);
            cachedAppAuthMapPathPattern.put(appId,uriRegxPathPattern);

        }
        //cachedAppAuthMap.putAll(newAppAuthenticationMap);
    }

    //
    @Override
    public Map<String, Set<String>> getAllAppAuthentications() {
        return null;
    }

    @Override
    public boolean match(String appId, String uri) {
        if(StringUtils.isEmpty(appId)){
            return false;
        }
        if(CollectionUtils.isEmpty(cachedAppAuthMapPathPattern.get(appId))){
            return false;
        }
        if(getRegxOfUri(appId,uri) != null)
            return true;
        return false;
    }

    private Set<PathPattern> parseUriSet(Set<String> uriSet){
        Set<PathPattern> uriRegxPathPattern = new HashSet<>();
        uriSet.stream().forEach(s ->{
            uriRegxPathPattern.add(pathPatternParser.parse(s));
        });
        return uriRegxPathPattern;
    }
}
