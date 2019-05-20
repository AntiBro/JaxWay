package com.gateway.jaxway.admin.dao.model;

import java.util.Date;

public class JaxwayRouteModel {
    private Integer id;

    private String routeId;

    private Integer jaxwayServerId;

    private String routeContent;

    private String predicateType;

    private String predicateValue;

    private String filterType;

    private String filterValue;

    private String url;

    private Integer opType;

    private Integer createUserId;

    private Date createTime;

    private Date updateTime;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

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

    public String getRouteContent() {
        return routeContent;
    }

    public void setRouteContent(String routeContent) {
        this.routeContent = routeContent;
    }

    public String getPredicateType() {
        return predicateType;
    }

    public void setPredicateType(String predicateType) {
        this.predicateType = predicateType;
    }

    public String getPredicateValue() {
        return predicateValue;
    }

    public void setPredicateValue(String predicateValue) {
        this.predicateValue = predicateValue;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
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