package com.gateway.jaxway.core.authority;

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
     * @param newAppAuthenticationMap
     */
    void updateAppAuthentications(Map<String,Set<String>> newAppAuthenticationMap);


    /**
     * 获取所有的 请求认证信息
     * @return
     */
    Map<String, Set<String>> getAllAppAuthentications();

    default boolean match(String url, String token){
        if(CollectionUtils.isEmpty(getAllAppAuthentications().get(url))){
            return false;
        }
        return getAllAppAuthentications().get(url).contains(token);
    }
}
