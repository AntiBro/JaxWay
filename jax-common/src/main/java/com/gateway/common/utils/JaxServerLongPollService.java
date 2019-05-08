package com.gateway.common.utils;

import com.gateway.common.beans.JaxServerAuthentication;

/**
 * @Author huaili
 * @Date 2019/5/8 20:32
 * @Description JaxServerLongPollService
 **/
public interface JaxServerLongPollService {
    /**
     * first, longpull for while app request on server side which does not need to go through jaxway server authority filter
     * then longpull for app request on server side which need to go through jaxway server authority filter
     *
     * todo:
     *   1. pull server white app list
     *   2. pull server authority app list
     * @param jaxServerAuthentication
     */
    void doLongPull(JaxServerAuthentication jaxServerAuthentication);
}
