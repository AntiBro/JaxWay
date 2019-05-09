package com.gateway.jaxway.core.route.support;

import com.gateway.common.JaxRouteDefinitionRepository;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxRouteDefinition;
import com.gateway.common.support.http.HttpUtil;
import com.gateway.common.support.http.JaxHttpRequest;
import com.gateway.common.support.http.JaxHttpResponseWrapper;
import org.springframework.core.ParameterizedTypeReference;

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

        ParameterizedTypeReference<JaxHttpResponseWrapper<JaxClientAuthentication>> responseBodyType = new ParameterizedTypeReference<JaxHttpResponseWrapper<JaxClientAuthentication>>() {};
        JaxHttpResponseWrapper<JaxClientAuthentication> responseWrapper = httpUtil.doGet(jaxHttpRequest,responseBodyType);

        return null;
    }
}
