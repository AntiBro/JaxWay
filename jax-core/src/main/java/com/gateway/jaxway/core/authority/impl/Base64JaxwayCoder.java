package com.gateway.jaxway.core.authority.impl;

import com.gateway.jaxway.core.authority.JaxwayCoder;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

import static com.gateway.jaxway.core.common.JaxwayConstant.JAXWAY_CLIENT_VALIDATOR_PREFIXX;

/**
 * @Author huaili
 * @Date 2019/4/17 18:02
 * @Description Base64JaxwayCoder 使用 base64 加密算法
 **/
public class Base64JaxwayCoder implements JaxwayCoder {

    private static String  UTF_8 = "UTF-8";

    @Override
    public String encode(String appName) throws UnsupportedEncodingException {
        String toEncodeAppName = JAXWAY_CLIENT_VALIDATOR_PREFIXX+appName;
        return new String(Base64.encodeBase64(toEncodeAppName.getBytes(UTF_8)));
    }

    @Override
    public String decode(String token) throws UnsupportedEncodingException {
        String decodeBase64 = new String(Base64.decodeBase64(token.getBytes(UTF_8)));
        return decodeBase64.substring(JAXWAY_CLIENT_VALIDATOR_PREFIXX.length());
    }
}
