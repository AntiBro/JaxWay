package com.gateway.jaxway.core.authority;

import com.gateway.jaxway.core.vo.JaxClientAuthentication;

/**
 * @Author huaili
 * @Date 2019/5/6 17:17
 * @Description JaxwayClientAuthenticationDataStore
 **/
public interface JaxwayClientAuthenticationDataStore extends JaxwayAuthenticationDataStore {
    /**
     * 增量更新
     * @param jaxAuthentication
     */
    void updateAppAuthentications(JaxClientAuthentication jaxAuthentication);
}
