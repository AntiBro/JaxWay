package com.gateway.jaxway.admin.service.impl;

import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.jaxway.admin.beans.JaxRouteDefinition;
import com.gateway.jaxway.admin.service.AppInfoService;
import com.gateway.jaxway.admin.support.SerializeDeserializeWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.CLIENT_APP_INFO_REDIS_KEY;

/**
 * @Author huaili
 * @Date 2019/5/13 15:20
 * @Description AppInfoServiceImpl
 **/
@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource(name = "redisTemplateProtoStuff")
    private RedisTemplate redisTemplate;

    @Override
    public List<JaxClientAuthentication> getJaxClientAuthentication(String appId, Long versionId) {

        String redisKey = CLIENT_APP_INFO_REDIS_KEY+appId;

        List<JaxClientAuthentication> jaxClientAuthenticationList = new ArrayList<>();

        Set<String> versionIds = redisTemplate.opsForHash().keys(redisKey);

        versionIds.stream().filter(e-> Long.parseLong(e)>versionId).forEach(e->{
            SerializeDeserializeWrapper serializeDeserializeWrapper = (SerializeDeserializeWrapper)redisTemplate.opsForHash().get(redisKey,e);
            jaxClientAuthenticationList.addAll((List<JaxClientAuthentication>)serializeDeserializeWrapper.getData());
        });

        return jaxClientAuthenticationList;
    }

    @Override
    public JaxServerAuthentication getJaxServerAuthentication(String appId, Long versionId) {
        return null;
    }

    @Override
    public JaxServerAuthentication getServerWhiteListInfo(String appId, Long versionId) {
        return null;
    }

    @Override
    public List<JaxRouteDefinition> getJaxRouteDefinitions(String appId, Long versionId) {
        return null;
    }
}
