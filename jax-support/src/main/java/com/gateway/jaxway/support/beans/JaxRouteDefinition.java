package com.gateway.jaxway.support.beans;

import com.gateway.common.beans.OpType;
import org.springframework.beans.BeanUtils;

/**
 * @Author huaili
 * @Date 2019/5/9 11:28
 * @Description JaxRouteDefinition
 **/
public class JaxRouteDefinition extends RouteDefinition implements Comparable<JaxRouteDefinition> {

    private OpType opType;

    private long versionId;

    public JaxRouteDefinition(){}

    public JaxRouteDefinition(RouteDefinition routeDefinition){
        BeanUtils.copyProperties(routeDefinition,this);
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public OpType getOpType() {
        return opType;
    }

    public void setOpType(OpType opType) {
        this.opType = opType;
    }

    @Override
    public int compareTo(JaxRouteDefinition o) {
        return  this.getVersionId()>=o.getVersionId()?1:-1;
    }
}
