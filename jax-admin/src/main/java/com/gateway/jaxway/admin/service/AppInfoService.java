package com.gateway.jaxway.admin.service;

import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.jaxway.support.beans.JaxRouteDefinition;

import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/13 14:59
 * @Description AppInfoService
 **/
public interface AppInfoService {

    /**
     * client side app authoirity info
     * @param appId
     * @param versionId
     * @return
     */
    List<JaxClientAuthentication> getJaxClientAuthentication(String jaxId,String appId, Integer versionId);

    /**
     * server side app authority info
     * @param appId
     * @param versionId
     * @return
     */
    List<JaxServerAuthentication> getJaxServerAuthentication(String appId, Integer versionId);


    /**
     * server side get the whiteList info
     * @param appId
     * @param versionId
     * @return
     */
    List<JaxServerAuthentication> getServerWhiteListInfo(String appId,Integer versionId);


    /**
     * server side get the route definitions
     * @param appId
     * @param versionId
     * @return
     */
    List<JaxRouteDefinition> getJaxRouteDefinitions(String appId, Integer versionId);
}
