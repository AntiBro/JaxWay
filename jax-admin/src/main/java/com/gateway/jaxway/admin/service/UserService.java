package com.gateway.jaxway.admin.service;

/**
 * @Author huaili
 * @Date 2019/5/15 16:42
 * @Description UserService
 **/
public interface UserService {

    boolean checkUser(String username,String pwd);

    boolean checkUserAuthority(String username,String path);

}
