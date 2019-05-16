package com.gateway.jaxway.admin.service.impl;

import com.gateway.common.JaxwayCoder;
import com.gateway.jaxway.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author huaili
 * @Date 2019/5/15 16:49
 * @Description UserServiceImpl
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JaxwayCoder jaxwayCoder;

    @Override
    public boolean checkUser(String username, String pwd) {
        return true;
    }

    @Override
    public boolean checkUserAuthority(String username, String path) {
        return false;
    }
}
