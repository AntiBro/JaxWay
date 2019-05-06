package com.gateway.jaxway.core.vo;

import com.gateway.jaxway.core.utils.http.OpType;

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

    private String url;

    private String token;

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
