package com.gateway.jaxway.core.authority;

/**
 * @Author huaili
 * @Date 2019/4/17 17:57
 * @Description JaxwayTokenCoder
 **/
public interface JaxwayTokenCoder {

    /**
     * Jaxway server 对 appName 进行加密
     * @param appName
     * @return
     */
    String encode(String appName);

    /**
     * Jaxway client 对token 进行解密
     * @param token
     * @return
     */
    String decode(String token);

}
