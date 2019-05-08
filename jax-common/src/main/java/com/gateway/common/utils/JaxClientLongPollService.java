package com.gateway.common.utils;

import com.gateway.common.JaxwayClientAuthenticationDataStore;

/**
 * @Author huaili
 * @Date 2019/4/23 17:56
 * @Description JaxClientLongPollService
 **/
public interface JaxClientLongPollService {

    void doLongPoll(JaxwayClientAuthenticationDataStore jaxwayAuthenticationDataStore);

}
