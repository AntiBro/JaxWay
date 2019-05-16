package com.gateway.jaxway.admin.service;

import com.gateway.jaxway.admin.dao.model.UserModel;

/**
 * @Author huaili
 * @Date 2019/5/15 16:42
 * @Description UserService
 **/
public interface UserService {

    UserModel checkUser(String username, String pwd);

    boolean checkUserAuthority(String username,String path);

}
