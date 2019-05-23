package com.gateway.jaxway.admin.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class JaxwayServerModel {
    private Integer id;

    private String jaxwayName;

    private String jaxwayDespc;

    private Integer createUserId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT-5")
    private Date updateTime;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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