package com.gateway.jaxway.admin.dao.mapper;

import com.gateway.jaxway.admin.dao.model.JaxwayWhiteListModel;

public interface JaxwayWhiteListModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JaxwayWhiteListModel record);

    int insertSelective(JaxwayWhiteListModel record);

    JaxwayWhiteListModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JaxwayWhiteListModel record);

    int updateByPrimaryKey(JaxwayWhiteListModel record);
}