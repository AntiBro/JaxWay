package com.gateway.jaxway.admin.service;


import com.gateway.jaxway.admin.support.SerializeDeserializeWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 封装redis操作
 */
@Service
public class RedisService {

    @Resource(name = "redisTemplateProtoStuff")
    private RedisTemplate<String, Object> template;

    /**
     * 存储hash结构的数据
     */
    public void putObject(String key, String hKey, Object value) {
        SerializeDeserializeWrapper wrapper = new SerializeDeserializeWrapper();
        wrapper.setData(value);
        template.opsForHash().put(key,hKey,wrapper);
    }

    /**
     * 获取数据
     */
    public <T> T getObject(String key, String hKey, Class<T> clazz)  {
        SerializeDeserializeWrapper wrapper = (SerializeDeserializeWrapper)template.opsForHash().get(key,hKey);
        return (T)wrapper.getData();
    }


}
