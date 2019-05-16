package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.GroupUserLinkModel;

public interface GroupUserLinkModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUserLinkModel record);

    int insertSelective(GroupUserLinkModel record);

    GroupUserLinkModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUserLinkModel record);

    int updateByPrimaryKey(GroupUserLinkModel record);
}