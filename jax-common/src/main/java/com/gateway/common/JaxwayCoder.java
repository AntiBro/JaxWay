package com.gateway.common;

import java.io.UnsupportedEncodingException;

/**
 * @Author huaili
 * @Date 2019/4/17 17:57
 * @Description JaxwayCoder
 **/
public interface JaxwayCoder {

    /**
     * Jaxway 进行加密
     * @param str
     * @return
     */
    String encode(String str) throws UnsupportedEncodingException;

    /**
     * Jaxway 进行解密
     * @param str
     * @return
     */
    String decode(String str) throws UnsupportedEncodingException;

}
