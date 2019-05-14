package com.gateway.jaxway.core.authority.server;

import com.gateway.common.JaxwayWhiteList;
import com.gateway.common.JaxwayWhiteListDataStore;
import com.gateway.common.beans.JaxServerAuthentication;

/**
 * @Author huaili
 * @Date 2019/5/10 18:23
 * @Description LocalJaxwayWhiteListDataStore
 **/
public class LocalJaxwayWhiteListDataStore implements JaxwayWhiteListDataStore {

    @Override
    public void updateWhiteList(JaxwayWhiteList jaxwayWhiteList, JaxServerAuthentication jaxServerAuthentication) {
        switch (jaxServerAuthentication.getOpType()){
            case ADD_WHITE_SERVER_APP:
                for(String uri:jaxServerAuthentication.getUriRegxSet()) {
                    jaxwayWhiteList.add(uri);
                }
                break;
            case DELETE_WHITE_SERVER_APP:
                for(String uri:jaxServerAuthentication.getUriRegxSet()){
                    jaxwayWhiteList.remove(uri);
                }
                break;
        }
    }
}
