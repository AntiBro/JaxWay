package com.gateway.jaxway.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.beans.RouteDefinition;
import com.gateway.jaxway.admin.dao.mapper.JaxwayRouteModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.RoutesService;
import com.gateway.jaxway.admin.util.RouteUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.REDIS_ROUTE_VERSION_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/17 16:39
 * @Description RoutesServiceImpl
 **/
@Service
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private JaxwayRouteModelMapper jaxwayRouteModelMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<JaxwayRouteModel> getTotalRoutesInfo(Integer jaxServerId,Integer verionId,RouteType routeType) {
        Integer totalCount  = jaxwayRouteModelMapper.selecTotalRoutesCountByJaxServerId(jaxServerId);
        int batchCount = 300;

        List<JaxwayRouteModel> ret = new ArrayList<>();

        if(totalCount == null || totalCount.equals(0)){
            return Collections.emptyList();
        }
        int page = totalCount/batchCount;
        if(totalCount%batchCount>0){
            page = page + 1;
        }
        int lcoalVersionId = 0;

        for(int i=1;i<=page;i++){
            PageHelper.startPage(i,batchCount);
            JaxwayRouteModel record = new JaxwayRouteModel();
            record.setId(verionId);
            record.setJaxwayServerId(jaxServerId);
            List<JaxwayRouteModel> historyList = jaxwayRouteModelMapper.selecRoutesInfoByJaxServerId(record);

            for(JaxwayRouteModel jaxwayRouteModel:historyList){
                // get the final routes
                if(routeType == RouteType.FINAL) {
                    OpType type = OpType.valueOf(jaxwayRouteModel.getOpType());
                    switch (type) {
                        case ADD_ROUTE:
                            ret.add(jaxwayRouteModel);
                            break;
                        case DELETE_ROUTE:
                            Iterator<JaxwayRouteModel> it = ret.iterator();
                            while (it.hasNext()) {
                                JaxwayRouteModel x = it.next();
                                if (x.getRouteId().equals(jaxwayRouteModel.getRouteId())) {
                                    it.remove();
                                }
                            }
                            break;
                    }
                }else{
                    // get history routes
                    ret.add(jaxwayRouteModel);
                }
                if(jaxwayRouteModel.getId()>lcoalVersionId){
                    lcoalVersionId = jaxwayRouteModel.getId();
                }
            }
        }

        for(JaxwayRouteModel item:ret){
            item.setVersionId(lcoalVersionId);
        }

        return ret;
    }

    @Override
    @Transactional
    public boolean insertRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws Exception {
        RouteDefinition rdf = RouteUtil.generateRouteDefition(jaxwayRouteModel.getRouteId(),
                jaxwayRouteModel.getUrl(),
                jaxwayRouteModel.getPredicateType()+"="+ jaxwayRouteModel.getPredicateValue(),
                jaxwayRouteModel.getFilterType()+"="+jaxwayRouteModel.getFilterValue());
        jaxwayRouteModel.setRouteContent(JSON.toJSONString(rdf));

        if(!RouteUtil.checkPathPatternList(rdf.getPredicates())){
            throw new Exception("predicate path value error");
        }
        int ret = jaxwayRouteModelMapper.insert(jaxwayRouteModel);
        if(ret==1){
            // update versionId redis cache
            updateVerionIdInRedis(jaxwayRouteModel.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean insertDeleRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException {
        int ret = jaxwayRouteModelMapper.insert(jaxwayRouteModel);
        if(ret==1){
            // update versionId redis cache
            updateVerionIdInRedis(jaxwayRouteModel.getId());
            return true;
        }
        return false;
    }

    public void updateVerionIdInRedis(Integer versionId){
        Integer cachedVersionId = (Integer) redisTemplate.opsForValue().get(REDIS_ROUTE_VERSION_ID_KEY);
        if(cachedVersionId ==null || versionId>cachedVersionId){
            redisTemplate.opsForValue().set(REDIS_ROUTE_VERSION_ID_KEY,versionId);
        }
    }
}
