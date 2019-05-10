package com.gateway.jaxway.core.route.support;

import com.gateway.common.JaxRouteDefinitionRepository;
import com.gateway.common.beans.JaxRouteDefinition;
import com.gateway.common.support.http.HttpUtil;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.common.support.http.JaxHttpResponseWrapper;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/5/9 15:46
 * @Description DefaultJaxRouteDefinitionRepository
 **/
public class DefaultJaxRouteDefinitionRepository implements JaxRouteDefinitionRepository {

    private HttpUtil httpUtil = HttpUtil.newInstance();

    @Override
    public List<JaxRouteDefinition> getJaxRouteDefinitions(JaxHttpRequest jaxHttpRequest) {

        ParameterizedTypeReference<JaxHttpResponseWrapper<List<JaxRouteDefinition>>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<List<JaxRouteDefinition>>>() {};
        JaxHttpResponseWrapper<List<JaxRouteDefinition>> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

        if (responseWrapper.getCode() == 200) {
            List<JaxRouteDefinition> jaxRouteDefinitionList = responseWrapper.getBody();
            Collections.sort(jaxRouteDefinitionList);
            return jaxRouteDefinitionList;
        }
        return null;
    }
}
