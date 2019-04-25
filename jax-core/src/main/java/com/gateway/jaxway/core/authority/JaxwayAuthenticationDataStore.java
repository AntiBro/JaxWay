package com.gateway.jaxway.core.authority;

import com.gateway.jaxway.core.utils.http.JaxAuthentication;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * @Author huaili
 * @Date 2019/4/17 20:21
 * @Description JaxwayAuthenticationDataStore
 **/
public interface JaxwayAuthenticationDataStore {

    /**
     * 全量 更新 本地的请求认证信息
     * @param newAppAppAuthenticationMap
     */
    void updateAppAuthentications(Map<String,Set<String>> newAppAppAuthenticationMap);


    /**
     * 增量更新
     * @param jaxAuthentication
     */
    void updateAppAuthentications(JaxAuthentication jaxAuthentication);

    /**
     * 获取所有的 请求认证信息
     * @return
     */
    Map<String, Set<String>> getAllAppAuthentications();

    default boolean contains(String url,String token){
        if(CollectionUtils.isEmpty(getAllAppAuthentications().get(url))){
            return false;
        }
        return getAllAppAuthentications().get(url).contains(token);
    }
}
