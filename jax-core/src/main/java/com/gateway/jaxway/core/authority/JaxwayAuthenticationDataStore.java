package com.gateway.jaxway.core.authority;

import java.util.Map;

/**
 * @Author huaili
 * @Date 2019/4/17 20:21
 * @Description JaxwayAuthenticationDataStore
 **/
public interface JaxwayAuthenticationDataStore {

    /**
     * 更新 本地的请求认证信息
     * @param newAppAppAuthenticationMap
     */
    void updateAppAuthentications(Map<String,String> newAppAppAuthenticationMap);

    /**
     * 获取所有的 请求认证信息
     * @return
     */
    Map<String,String> getAllAppAuthentications();

    default boolean contains(String url,String token){
        return getAllAppAuthentications().get(url).equals(token);
    }
}
