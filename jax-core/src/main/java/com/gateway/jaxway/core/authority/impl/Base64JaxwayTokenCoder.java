package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.core.common.JaxwayConstant;
import org.apache.commons.codec.binary.Base64;

/**
 * @Author huaili
 * @Date 2019/4/17 18:02
 * @Description Base64JaxwayTokenCoder 使用 base64 加密算法
 **/
public class Base64JaxwayTokenCoder implements JaxwayTokenCoder {


    @Override
    public String encode(String appName) {
        String toEncodeAppName = JaxwayConstant.JAXWAY_CLIENT_VALIDATOR_PREFIXX+appName;
        return new String(Base64.encodeBase64(toEncodeAppName.getBytes()));
    }

    @Override
    public String decode(String token) {
        return new String(Base64.decodeBase64(token.getBytes()));
    }
}
