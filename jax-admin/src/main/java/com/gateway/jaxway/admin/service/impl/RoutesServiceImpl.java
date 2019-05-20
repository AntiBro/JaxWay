package com.gateway.jaxway.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.gateway.jaxway.admin.beans.RouteDefinition;
import com.gateway.jaxway.admin.dao.mapper.JaxwayRouteModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.RoutesService;
import com.gateway.jaxway.admin.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/17 16:39
 * @Description RoutesServiceImpl
 **/
@Service
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private JaxwayRouteModelMapper jaxwayRouteModelMapper;


    @Override
    public List<JaxwayRouteModel> getTotalRoutesInfo(String jaxServerId) {
        return null;
    }

    @Override
    public boolean insertRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException {
        RouteDefinition rdf = RouteUtil.generateRouteDefition(jaxwayRouteModel.getRouteId(),
                jaxwayRouteModel.getUrl(),
                jaxwayRouteModel.getPredicateType()+"="+ jaxwayRouteModel.getPredicateValue(),
                jaxwayRouteModel.getFilterType()+"="+jaxwayRouteModel.getFilterValue());
        jaxwayRouteModel.setRouteContent(JSON.toJSONString(rdf));
        return jaxwayRouteModelMapper.insert(jaxwayRouteModel) == 1?true:false;
    }
}
