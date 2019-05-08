package com.gateway.common;

import com.gateway.common.beans.JaxRequest;

import java.io.UnsupportedEncodingException;

/**
 * @Author huaili
 * @Date 2019/4/17 17:19
 * @Description JaxwayClientValidator 验证从JaywayServer过来的请求的合法性
 **/
public interface JaxwayClientValidator {

    /**
     * 先从头部的 请求字段中解析出原始请求ip和经过网关的权限Token,然后进行请求的权限认证
     * @param jaxRequest
     * @return
     */
    boolean validate(JaxRequest jaxRequest) throws UnsupportedEncodingException;
}
