package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayGroupModel;

public interface JaxwayGroupModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayGroupModel record);

    int insertSelective(JaxwayGroupModel record);

    JaxwayGroupModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JaxwayGroupModel record);

    int updateByPrimaryKey(JaxwayGroupModel record);
}