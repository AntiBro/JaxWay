package com.gateway.jaxway.core.authority;

/**
 * @Author huaili
 * @Date 2019/4/17 17:19
 * @Description JaxwayClientValidator 验证从JaywayServer过来的请求的合法性
 **/
public interface JaxwayClientValidator {

    /**
     * 先从头部的 请求字段中解析出原始请求ip和经过网关的权限Token,然后进行请求的权限认证
     * @param requestUrl
     * @param token
     * @return
     */
    boolean validate(String requestUrl,String token);
}
