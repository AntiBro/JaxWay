package com.gateway.jaxway.admin.support;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.EMPTY_BYTE_ARRAY;

/**
 * @Author huaili
 * @Date 2018/12/26 21:31
 * @Description Redis 的 protostuff 的实现类
 **/
public class ProtostuffRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> type;

    public ProtostuffRedisSerializer(Class<T> type){
        this.type = type;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return EMPTY_BYTE_ARRAY;
        }
        return ProtoStuffUtils.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return ProtoStuffUtils.deserialize(bytes,this.type);
    }
}
