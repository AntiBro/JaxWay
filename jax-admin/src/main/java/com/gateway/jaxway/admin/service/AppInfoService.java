package com.gateway.jaxway.admin.service;

import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.jaxway.admin.beans.JaxRouteDefinition;

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
    List<JaxClientAuthentication> getJaxClientAuthentication(String appId, Long versionId);

    /**
     * server side app authority info
     * @param appId
     * @param versionId
     * @return
     */
    JaxServerAuthentication getJaxServerAuthentication(String appId, Long versionId);


    /**
     * server side get the whiteList info
     * @param appId
     * @param versionId
     * @return
     */
    JaxServerAuthentication getServerWhiteListInfo(String appId,Long versionId);


    /**
     * server side get the route definitions
     * @param appId
     * @param versionId
     * @return
     */
    List<JaxRouteDefinition> getJaxRouteDefinitions(String appId,Long versionId);
}
