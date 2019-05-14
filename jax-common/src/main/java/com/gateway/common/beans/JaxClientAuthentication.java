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

    private String pathPattern;

    public String getAppId() {
        return appId;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }
}
