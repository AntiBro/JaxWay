package com.gateway.jaxway.admin.vo;

import org.springframework.core.style.ToStringCreator;

/**
 * @Author huaili
 * @Date 2019/5/15 18:25
 * @Description UserRequestParamsVO
 **/
public class UserRequestParamsVO {
    private String username;
    private String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return new  ToStringCreator(this).
                append("username",getUsername()).
                append("pwd",getPwd())
                .toString();
    }
}
