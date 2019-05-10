package com.gateway.common;

import com.gateway.common.beans.JaxServerAuthentication;

/**
 * @Author huaili
 * @Date 2019/5/10 18:19
 * @Description JaxwayWhiteListDataStore
 **/
public interface JaxwayWhiteListDataStore {

    void updateWhiteList(JaxwayWhiteList jaxwayWhiteList, JaxServerAuthentication jaxServerAuthentication);

}
