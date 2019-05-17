package com.gateway.jaxway.admin.service.impl;

import com.gateway.jaxway.admin.dao.mapper.JaxwayRouteModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/17 16:39
 * @Description RoutesServiceImpl
 **/
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private JaxwayRouteModelMapper jaxwayRouteModelMapper;


    @Override
    public List<JaxwayRouteModel> getTotalRoutesInfo(String jaxServerId) {
        return null;
    }
}
