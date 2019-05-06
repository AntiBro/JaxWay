package com.gateway.jaxway.core.authority;

/**
 * @Author huaili
 * @Date 2019/5/6 14:11
 * @Description JaxwayWhiteList
 **/
public interface JaxwayWhiteList {

    void add(String uriRegx);

    void remove(String uriRegx);

    boolean match(String uri);
}
