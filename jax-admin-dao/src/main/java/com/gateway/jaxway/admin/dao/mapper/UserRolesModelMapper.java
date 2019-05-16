package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.UserRolesModel;

public interface UserRolesModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRolesModel record);

    int insertSelective(UserRolesModel record);

    UserRolesModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRolesModel record);

    int updateByPrimaryKey(UserRolesModel record);
}