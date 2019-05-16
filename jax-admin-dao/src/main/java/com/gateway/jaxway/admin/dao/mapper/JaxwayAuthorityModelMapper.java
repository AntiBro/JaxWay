package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayAuthorityModel;

public interface JaxwayAuthorityModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayAuthorityModel record);

    int insertSelective(JaxwayAuthorityModel record);

    JaxwayAuthorityModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JaxwayAuthorityModel record);

    int updateByPrimaryKey(JaxwayAuthorityModel record);
}