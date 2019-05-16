package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.UserModel;

public interface UserModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Integer id);

    UserModel selectUserByNameAndPsw(UserModel record);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);
}