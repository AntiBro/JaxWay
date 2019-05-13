package com.gateway.common.defaults;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.JaxwayServerAuthenticationDataStore;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.jaxway.log.Log;
import com.gateway.jaxway.log.impl.DefaultLogImpl;
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

    private Log logger = new DefaultLogImpl(LocalJaxwayAuthenticationServerDataStore.class);

    private PathPatternParser pathPatternParser = new PathPatternParser();

    private JaxwayCoder jaxwayCoder;

    private LocalJaxwayAuthenticationServerDataStore(){
        this.jaxwayCoder = new Base64JaxwayCoder();
    }

    public static JaxwayServerAuthenticationDataStore create(){
        return INSTANCE;
    }


    private static volatile Map<String,Set<PathPattern>> cachedAppAuthMapPathPattern = new ConcurrentHashMap<>();

    @Override
    public void updateAppAuthentications(JaxServerAuthentication jaxServerAuthentication) {

        // decode the appid and uri sets
        try{
            jaxServerAuthentication.setAppId(jaxwayCoder.decode(jaxServerAuthentication.getAppId()));
            Set<String> uriSet = jaxServerAuthentication.getUriRegxSet();
            Set<String> decodeUriSets = new HashSet<>();
            for(String uri:uriSet){
                uri = jaxwayCoder.decode(uri);
                decodeUriSets.add(uri);
            }
            jaxServerAuthentication.setUriRegxSet(decodeUriSets);
        }catch(Exception e){
            logger.log(Log.LogType.ERROR,e.getMessage());
            return;
        }

        switch (jaxServerAuthentication.getOpType()){
            case ADD_APP:
                Set<PathPattern> uriRegx;
                if(CollectionUtils.isEmpty(cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId()))){
                    uriRegx = new HashSet<>();
                    cachedAppAuthMapPathPattern.put(jaxServerAuthentication.getAppId(),uriRegx);
                }else{
                    uriRegx = cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId());
                }
                if( !CollectionUtils.isEmpty(jaxServerAuthentication.getUriRegxSet())) {
                    uriRegx.addAll(parseUriSet(jaxServerAuthentication.getUriRegxSet()));
                    logger.log("jax-way server add app authentication appId={} uriSets={}",jaxServerAuthentication.getAppId(),jaxServerAuthentication.getUriRegxSet());
                }
                break;
            case DELETE_APP:
                if(!CollectionUtils.isEmpty(cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId()))){
                    logger.log("jax-way server delete app authentication info={}",jaxServerAuthentication.getUriRegxSet());
                    Set<PathPattern> uriRegxPathPattern = cachedAppAuthMapPathPattern.get(jaxServerAuthentication.getAppId());
                    uriRegxPathPattern.removeAll(parseUriSet(jaxServerAuthentication.getUriRegxSet()));
                }
                break;
            case NONE:
                logger.log("jax-way server app authentication OpType=NONE");
                break;
            default:
                logger.log(Log.LogType.ERROR,"jax-way server inlegal app authentication OpType={}",jaxServerAuthentication.getOpType());
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
