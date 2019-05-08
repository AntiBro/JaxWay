package com.gateway.common.beans;

import java.util.Set;

/**
 * @Author huaili
 * @Date 2019/5/6 17:30
 * @Description JaxServerAuthentication
 **/
public class JaxServerAuthentication extends JaxAuthentication {
    public static JaxServerAuthentication NONE = new JaxServerAuthentication(OpType.NONE);

    public JaxServerAuthentication(){}

    public JaxServerAuthentication(OpType opType){
        super(opType);
    }

    private String appId;


    private Set<String> uriRegxSet;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public Set<String> getUriRegxSet() {
        return uriRegxSet;
    }

    public void setUriRegxSet(Set<String> uriRegxSet) {
        this.uriRegxSet = uriRegxSet;
    }
}
