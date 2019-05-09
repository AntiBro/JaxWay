package com.gateway.common.support;

import com.gateway.common.JaxwayClientAuthenticationDataStore;

/**
 * @Author huaili
 * @Date 2019/4/23 17:56
 * @Description JaxClientLongPullService
 **/
public interface JaxClientLongPullService {

    /**
     * pull (appid,pathPattern) map for jax-way client which enable the jax-way authority filter
     * @param jaxwayAuthenticationDataStore
     */
    void doLongPull(JaxwayClientAuthenticationDataStore jaxwayAuthenticationDataStore);

}
