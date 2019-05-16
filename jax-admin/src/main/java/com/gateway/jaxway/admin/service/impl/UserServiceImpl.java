package com.gateway.jaxway.admin.service.impl;

import com.gateway.common.JaxwayCoder;
import com.gateway.jaxway.admin.dao.mapper.UserModelMapper;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Author huaili
 * @Date 2019/5/15 16:49
 * @Description UserServiceImpl
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JaxwayCoder jaxwayCoder;

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public UserModel checkUser(String username, String pwd) {
        UserModel query = new UserModel();
        query.setUserName(username);
        try {
            query.setPsw(jaxwayCoder.encode(username+jaxwayCoder.decode(pwd)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return userModelMapper.selectUserByNameAndPsw(query);
    }

    @Override
    public boolean checkUserAuthority(String username, String path) {
        return false;
    }
}
