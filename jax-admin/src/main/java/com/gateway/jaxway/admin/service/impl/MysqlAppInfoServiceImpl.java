package com.gateway.jaxway.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.AppInfoService;
import com.gateway.jaxway.admin.service.RoutesService;
import com.gateway.jaxway.support.beans.JaxRouteDefinition;
import com.gateway.jaxway.support.beans.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.REDIS_ROUTE_VERSION_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/22 11:15
 * @Description MysqlAppInfoServiceImpl
 **/
@Service
@Primary
public class MysqlAppInfoServiceImpl implements AppInfoService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoutesService routesService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<JaxClientAuthentication> getJaxClientAuthentication(String jaxId, String appId, Integer versionId) {
        return Collections.emptyList();
    }

    @Override
    public List<JaxServerAuthentication> getJaxServerAuthentication(String appId, Integer versionId) {
        return Collections.emptyList();
    }

    @Override
    public List<JaxServerAuthentication> getServerWhiteListInfo(String appId, Integer versionId) {
        return Collections.emptyList();
    }

    @Override
    public List<JaxRouteDefinition> getJaxRouteDefinitions(String appId, Integer versionId) {
        Integer cachedVersionId = (Integer) redisTemplate.opsForValue().get(REDIS_ROUTE_VERSION_ID_KEY);


        if(cachedVersionId!=null && cachedVersionId<=versionId){
            return Collections.emptyList();
        }

        List<JaxRouteDefinition> ret = new ArrayList<>();

        // get history Routes for Jaxway Server
        List<JaxwayRouteModel> list = routesService.getTotalRoutesInfo(Integer.parseInt(appId),versionId, RoutesService.RouteType.HISTORY);
        int localMaxVerionID = cachedVersionId;
        for(JaxwayRouteModel item:list){
            // build JaxRouteDefinition from  routeContent in Mysql
            JaxRouteDefinition jaxRouteDefinition = new JaxRouteDefinition();
            RouteDefinition routeDefinition =JSON.parseObject(item.getRouteContent(),RouteDefinition.class);
            jaxRouteDefinition.setOpType(OpType.valueOf(item.getOpType()));

            jaxRouteDefinition.setId(routeDefinition.getId());
            jaxRouteDefinition.setUri(routeDefinition.getUri());
            jaxRouteDefinition.setFilters(routeDefinition.getFilters());
            jaxRouteDefinition.setPredicates(routeDefinition.getPredicates());
            jaxRouteDefinition.setOrder(routeDefinition.getOrder());
            ret.add(jaxRouteDefinition);

            // update versionId
            if(item.getVersionId()>localMaxVerionID ){
                localMaxVerionID = item.getVersionId();
            }
            jaxRouteDefinition.setVersionId(localMaxVerionID);
        }

        logger.info("local versionId={}",cachedVersionId);
        if(cachedVersionId == null||localMaxVerionID>cachedVersionId){
            redisTemplate.opsForValue().set(REDIS_ROUTE_VERSION_ID_KEY,localMaxVerionID);
        }

        return ret;
    }
}
