package com.gateway.jaxway.admin.dao.model;

import java.util.Date;

public class JaxwayAuthorityModel {
    private Integer id;

    private Integer jaxwayServerId;

    private Integer jaxwayAppId;

    private String pathPattern;

    private Integer groupId;

    private Integer opType;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJaxwayServerId() {
        return jaxwayServerId;
    }

    public void setJaxwayServerId(Integer jaxwayServerId) {
        this.jaxwayServerId = jaxwayServerId;
    }

    public Integer getJaxwayAppId() {
        return jaxwayAppId;
    }

    public void setJaxwayAppId(Integer jaxwayAppId) {
        this.jaxwayAppId = jaxwayAppId;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}