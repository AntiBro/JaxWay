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
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/17 16:39
 * @Description RoutesServiceImpl
 **/
@Service
public class RoutesServiceImpl implements RoutesService {

    @Autowired
    private JaxwayRouteModelMapper jaxwayRouteModelMapper;


    @Override
    public List<JaxwayRouteModel> getTotalRoutesInfo(Integer jaxServerId) {
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

        for(int i=1;i<=page;i++){
            PageHelper.startPage(i,batchCount);
            List<JaxwayRouteModel> historyList = jaxwayRouteModelMapper.selecRoutesInfoByJaxServerId(jaxServerId);

            for(JaxwayRouteModel jaxwayRouteModel:historyList){
                OpType type = OpType.valueOf(jaxwayRouteModel.getOpType());
                switch (type){
                    case ADD_ROUTE:
                        ret.add(jaxwayRouteModel);
                        break;
                    case DELETE_ROUTE:
                        Iterator<JaxwayRouteModel> it = ret.iterator();
                        while(it.hasNext()){
                            JaxwayRouteModel x = it.next();
                            if(x.getRouteId().equals(jaxwayRouteModel.getRouteId())){
                                it.remove();
                            }
                        }
                        break;
                }
            }
        }

        return ret;
    }

    @Override
    public boolean insertRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException {
        RouteDefinition rdf = RouteUtil.generateRouteDefition(jaxwayRouteModel.getRouteId(),
                jaxwayRouteModel.getUrl(),
                jaxwayRouteModel.getPredicateType()+"="+ jaxwayRouteModel.getPredicateValue(),
                jaxwayRouteModel.getFilterType()+"="+jaxwayRouteModel.getFilterValue());
        jaxwayRouteModel.setRouteContent(JSON.toJSONString(rdf));
        return jaxwayRouteModelMapper.insert(jaxwayRouteModel) == 1?true:false;
    }

    @Override
    public boolean insertDeleRouteDefinition(JaxwayRouteModel jaxwayRouteModel) throws URISyntaxException {
        return jaxwayRouteModelMapper.insert(jaxwayRouteModel) == 1?true:false;
    }
}
