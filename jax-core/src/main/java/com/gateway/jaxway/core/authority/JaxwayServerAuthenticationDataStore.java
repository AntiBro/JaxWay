package com.gateway.jaxway.core.authority;

import com.gateway.jaxway.core.vo.JaxServerAuthentication;

/**
 * @Author huaili
 * @Date 2019/5/6 17:34
 * @Description JaxwayServerAuthenticationDataStore
 **/
public interface JaxwayServerAuthenticationDataStore extends JaxwayAuthenticationDataStore {

    void updateAppAuthentications(JaxServerAuthentication jaxServerAuthentication);

    String getRegxOfUri(String appId,String uri);
}
