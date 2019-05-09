package com.gateway.common;

import com.gateway.common.beans.JaxRouteDefinition;
import com.gateway.common.support.http.JaxHttpRequest;

import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/9 11:37
 * @Description JaxRouteDefinitionRepository
 **/
public interface JaxRouteDefinitionRepository {

    List<JaxRouteDefinition> getJaxRouteDefinitions(JaxHttpRequest jaxHttpRequest);

}
