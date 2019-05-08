package com.gateway.common.beans;

/**
 * @Author huaili
 * @Date 2019/5/6 17:24
 * @Description JaxClientAuthentication
 **/
public class JaxClientAuthentication extends JaxAuthentication{

    public static JaxClientAuthentication NONE = new JaxClientAuthentication(OpType.NONE);

    public JaxClientAuthentication(){}

    public JaxClientAuthentication(OpType opType){
        super(opType);
    }

    private String appId;

    private String token;

    public String getAppId() {
        return appId;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
