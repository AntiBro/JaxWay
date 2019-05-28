package com.gateway.jaxway.admin.util;

import com.alibaba.fastjson.JSON;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.support.beans.JaxRouteDefinition;
import com.gateway.jaxway.support.beans.RouteDefinition;

/**
 * @Author huaili
 * @Date 2019/5/27 21:05
 * @Description ModelUtil
 **/
public class ModelUtil {
    public static JaxRouteDefinition convertJaxwayRouteModel(JaxwayRouteModel jaxwayRouteModel){
        JaxRouteDefinition jaxRouteDefinition = new JaxRouteDefinition();
        RouteDefinition routeDefinition = JSON.parseObject(jaxwayRouteModel.getRouteContent(),RouteDefinition.class);
        jaxRouteDefinition.setOpType(OpType.valueOf(jaxwayRouteModel.getOpType()));

        jaxRouteDefinition.setId(routeDefinition.getId());
        jaxRouteDefinition.setUri(routeDefinition.getUri());
        jaxRouteDefinition.setFilters(routeDefinition.getFilters());
        jaxRouteDefinition.setPredicates(routeDefinition.getPredicates());
        jaxRouteDefinition.setOrder(routeDefinition.getOrder());
        return jaxRouteDefinition;
    }
}
