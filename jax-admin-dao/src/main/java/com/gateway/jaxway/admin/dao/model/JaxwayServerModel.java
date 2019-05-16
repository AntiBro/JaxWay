package com.gateway.jaxway.admin.dao.model;

import java.util.Date;

public class JaxwayServerModel {
    private Integer id;

    private String jaxwayName;

    private String jaxwayDespc;

    private Integer createUserId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJaxwayName() {
        return jaxwayName;
    }

    public void setJaxwayName(String jaxwayName) {
        this.jaxwayName = jaxwayName;
    }

    public String getJaxwayDespc() {
        return jaxwayDespc;
    }

    public void setJaxwayDespc(String jaxwayDespc) {
        this.jaxwayDespc = jaxwayDespc;
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