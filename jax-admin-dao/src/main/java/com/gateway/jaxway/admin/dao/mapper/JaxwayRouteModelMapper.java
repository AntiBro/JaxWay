package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;

import java.util.List;

public interface JaxwayRouteModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayRouteModel record);

    int insertSelective(JaxwayRouteModel record);

    JaxwayRouteModel selectByPrimaryKey(Integer id);


    List<JaxwayRouteModel> selecRoutesInfoByJaxServerId(JaxwayRouteModel record);

    Integer selecTotalRoutesCountByJaxServerId(Integer jaxwayServerId);

    int updateByPrimaryKeySelective(JaxwayRouteModel record);

    int updateByPrimaryKey(JaxwayRouteModel record);
}