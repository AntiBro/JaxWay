package com.gateway.common.beans;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @Author huaili
 * @Date 2019/5/9 11:28
 * @Description JaxRouteDefinition
 **/
public class JaxRouteDefinition extends RouteDefinition {

    private OpType opType;

    public OpType getOpType() {
        return opType;
    }

    public void setOpType(OpType opType) {
        this.opType = opType;
    }
}
