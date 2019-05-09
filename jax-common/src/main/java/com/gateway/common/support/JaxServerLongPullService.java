package com.gateway.common.support;

import com.gateway.common.JaxRouteDefinitionRepository;
import com.gateway.common.JaxwayServerAuthenticationDataStore;
import com.gateway.common.JaxwayWhiteList;

/**
 * @Author huaili
 * @Date 2019/5/8 20:32
 * @Description JaxServerLongPullService
 *   1. pull server white app list
 *   2. pull server authority app list
 *   3. pull Route Definition
 **/
public interface JaxServerLongPullService {
    /**
     * pull server white app list
     * @param jaxwayWhiteList
     */
    void doLongPull(JaxwayWhiteList jaxwayWhiteList);

    /**
     * pull server authority app list
     * @param jaxwayServerAuthenticationDataStore
     */
    void doLongPull(JaxwayServerAuthenticationDataStore jaxwayServerAuthenticationDataStore);


    /**
     * pull Route definition from JaxRouteDefitionRepository
     * @param jaxRouteDefinitionRepository
     */
    void doLongPull(JaxRouteDefinitionRepository jaxRouteDefinitionRepository);
}
