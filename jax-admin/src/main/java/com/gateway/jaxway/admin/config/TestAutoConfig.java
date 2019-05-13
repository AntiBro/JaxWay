package com.gateway.jaxway.admin.config;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.JaxServerAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.beans.JaxRouteDefinition;
import com.gateway.jaxway.admin.util.RouteUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.*;

/**
 * @Author huaili
 * @Date 2019/5/13 15:33
 * @Description TestAutoConfig
 **/
@Component
public class TestAutoConfig {
    @Resource(name = "redisTemplateProtoStuff")
    private RedisTemplate redisTemplate;

    @Autowired
    private JaxwayCoder jaxwayCoder;


    @PostConstruct
    public void init() throws UnsupportedEncodingException, URISyntaxException {
        initClientInfos();
        initServerWhiteList();
        initServerAppInfo();
        initServerRoutesInfo();

       // redisTemplate.opsForZSet().add(,)
       // GenericObjectPoolConfig genericObjectPoolConfig;
    }


    void initClientInfos() throws UnsupportedEncodingException {
        List<JaxClientAuthentication> jaxClientAuthenticationList = new ArrayList<>();

        long versionId = 1;

        JaxClientAuthentication jaxAuthentication = new JaxClientAuthentication();
        String appId = "test";
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setToken(jaxwayCoder.encode("/sohu/**"));
        jaxAuthentication.setVersionId(versionId);


        JaxClientAuthentication jaxAuthentication2 = new JaxClientAuthentication();

        jaxAuthentication2.setOpType(OpType.ADD_APP);
        jaxAuthentication2.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication2.setPublishDate(new Date());
        jaxAuthentication2.setPublisher("meme");
        jaxAuthentication2.setToken(jaxwayCoder.encode("/testflux/**"));
        jaxAuthentication2.setVersionId(versionId);

        jaxClientAuthenticationList.add(jaxAuthentication);
        jaxClientAuthenticationList.add(jaxAuthentication2);

        redisTemplate.opsForHash().put(CLIENT_APP_INFO_REDIS_KEY+appId,String.valueOf(versionId), jaxClientAuthenticationList);
    }


    void initServerAppInfo() throws UnsupportedEncodingException {
        List<JaxServerAuthentication> jaxServerAuthenticationList = new ArrayList<>();

        long versionId = 1;
        JaxServerAuthentication jaxAuthentication = new JaxServerAuthentication();
        Set<String> uriSets = new HashSet<>();

        String serverappId = "jax-way-one";
        uriSets.add(jaxwayCoder.encode("/testflux/**"));

        jaxAuthentication.setUriRegxSet(uriSets);
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode("test"));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili123");
        jaxAuthentication.setVersionId(versionId);

        jaxAuthentication.setServerId(serverappId);

        jaxServerAuthenticationList.add(jaxAuthentication);

        redisTemplate.opsForHash().put(SERVER_APP_INFO_REDIS_KEY+serverappId,String.valueOf(versionId), jaxServerAuthenticationList);

    }
    void initServerWhiteList() throws UnsupportedEncodingException {
        List<JaxServerAuthentication> jaxServerAuthenticationList = new ArrayList<>();

        long versionId = 1;

        JaxServerAuthentication jaxAuthentication = new JaxServerAuthentication();
        Set<String> uriSets = new HashSet<>();

        String serverappId = "jax-way-one";
        uriSets.add(jaxwayCoder.encode("/sogou/**"));
        uriSets.add(jaxwayCoder.encode("/sohu/**"));
        uriSets.add(jaxwayCoder.encode("/sohu"));

        jaxAuthentication.setUriRegxSet(uriSets);
        jaxAuthentication.setOpType(OpType.ADD_WHITE_SERVER_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(serverappId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("li1798li");
        jaxAuthentication.setVersionId(versionId);

        jaxServerAuthenticationList.add(jaxAuthentication);

        redisTemplate.opsForHash().put(SERVER_WHITE_LIST_REDIS_KEY+serverappId,String.valueOf(versionId), jaxServerAuthenticationList);


    }

    void initServerRoutesInfo() throws URISyntaxException {

        String appId = "jax-way-one";
        long versionId = 1;
        List<JaxRouteDefinition> jaxRouteDefinitions = new ArrayList<>();
        JaxRouteDefinition jaxRouteDefinition = new JaxRouteDefinition();
        jaxRouteDefinition.setVersionId(versionId);
        jaxRouteDefinition.setOpType(OpType.ADD_ROUTE);

        BeanUtils.copyProperties(RouteUtil.generatePathRouteDefition("http://127.0.0.1:8720","/testflux,/testflux/**"),jaxRouteDefinition);

        jaxRouteDefinitions.add(jaxRouteDefinition);


        redisTemplate.opsForHash().put(SERVER_ROUTES_INFO_REDIS_KEY+appId,String.valueOf(versionId), jaxRouteDefinitions);

    }
}
