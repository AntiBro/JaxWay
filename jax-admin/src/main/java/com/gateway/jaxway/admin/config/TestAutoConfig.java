package com.gateway.jaxway.admin.config;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.jaxway.admin.support.SerializeDeserializeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.CLIENT_APP_INFO_REDIS_KEY;

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
    public void init() throws UnsupportedEncodingException {
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
        jaxAuthentication2.setToken(jaxwayCoder.encode("/sogou/**"));
        jaxAuthentication2.setVersionId(versionId);

        jaxClientAuthenticationList.add(jaxAuthentication);
        jaxClientAuthenticationList.add(jaxAuthentication2);

        redisTemplate.opsForHash().put(CLIENT_APP_INFO_REDIS_KEY+appId,String.valueOf(versionId), jaxClientAuthenticationList);

       // redisTemplate.opsForZSet().add(,)
       // GenericObjectPoolConfig genericObjectPoolConfig;
    }
}
