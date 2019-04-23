package com.gateway.jaxway.core.utils;

import com.gateway.jaxway.core.authority.JaxwayAuthenticationDataStore;

/**
 * @Author huaili
 * @Date 2019/4/23 17:56
 * @Description LongPollService
 **/
public interface LongPollService {

    void doLongPoll(JaxwayAuthenticationDataStore jaxwayAuthenticationDataStore);

}
