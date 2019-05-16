package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;

public interface JaxwayRouteModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayRouteModel record);

    int insertSelective(JaxwayRouteModel record);

    JaxwayRouteModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JaxwayRouteModel record);

    int updateByPrimaryKey(JaxwayRouteModel record);
}