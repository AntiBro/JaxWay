package com.gateway.jaxway.admin.service.impl;

import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.RoutesService;

import java.net.URISyntaxException;
import java.util.List;

// todo: redis routes service
public class RedisRoutesServiceImpl implements RoutesService {

    @Override
    public List<JaxwayRouteModel> getTotalRoutesInfo(Integer jaxServerId, Integer versionId, RouteType routeType) {
        return null;
    }

    @Override
    public boolean insertRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws Exception {
        return false;
    }

    @Override
    public boolean insertDeleRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException {
        return false;
    }
}
