package com.gateway.jaxway.admin.support;

import java.io.Serializable;

/**
 * protostuff的序列化包装类
 **/
public class SerializeDeserializeWrapper<T> implements Serializable {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SerializeDeserializeWrapper<T> builder(T data) {
        SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
        wrapper.setData(data);
        return wrapper;
    }

}
