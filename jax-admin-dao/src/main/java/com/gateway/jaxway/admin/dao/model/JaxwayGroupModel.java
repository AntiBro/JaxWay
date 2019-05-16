package com.gateway.jaxway.admin.dao.model;

import java.util.Date;

public class JaxwayGroupModel {
    private Integer id;

    private String groupName;

    private String groupDespc;

    private Integer jaxwayId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDespc() {
        return groupDespc;
    }

    public void setGroupDespc(String groupDespc) {
        this.groupDespc = groupDespc;
    }

    public Integer getJaxwayId() {
        return jaxwayId;
    }

    public void setJaxwayId(Integer jaxwayId) {
        this.jaxwayId = jaxwayId;
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