package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayAppInfoModel;

public interface JaxwayAppInfoModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayAppInfoModel record);

    int insertSelective(JaxwayAppInfoModel record);

    JaxwayAppInfoModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JaxwayAppInfoModel record);

    int updateByPrimaryKey(JaxwayAppInfoModel record);
}