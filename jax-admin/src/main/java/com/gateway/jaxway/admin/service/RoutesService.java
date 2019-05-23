package com.gateway.jaxway.admin.service;

import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/17 16:37
 * @Description RoutesService
 **/
public interface RoutesService {

     enum RouteType{
         FINAL,
         HISTORY;
     }

    List<JaxwayRouteModel> getTotalRoutesInfo(Integer jaxServerId,Integer versionId,RouteType routeType);

    boolean insertRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws Exception;


    boolean insertDeleRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException;


}
