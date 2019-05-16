package com.gateway.jaxway.admin.dao.model;

import java.util.Date;

public class JaxwayAppInfoModel {
    private Integer id;

    private String appName;

    private String appDespc;

    private Integer groupId;

    private Integer jaxwayServerId;

    private Integer createUserId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDespc() {
        return appDespc;
    }

    public void setAppDespc(String appDespc) {
        this.appDespc = appDespc;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getJaxwayServerId() {
        return jaxwayServerId;
    }

    public void setJaxwayServerId(Integer jaxwayServerId) {
        this.jaxwayServerId = jaxwayServerId;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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