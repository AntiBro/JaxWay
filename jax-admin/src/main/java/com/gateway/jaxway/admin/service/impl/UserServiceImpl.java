package com.gateway.jaxway.admin.service.impl;

import com.gateway.common.JaxwayCoder;
import com.gateway.jaxway.admin.dao.mapper.JaxwayServerModelMapper;
import com.gateway.jaxway.admin.dao.mapper.UserModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayServerModel;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

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

    @Autowired
    private JaxwayServerModelMapper jaxwayServerModelMapper;

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


    @Transactional
    @Override
    public void insertUser(UserInfoVO userInfoVO) {
        UserModel userModel = userInfoVO.transferToCommonUserModel();
        try {
           // userModel.setPsw(jaxwayCoder.encode(userModel.getUserName()+jaxwayCoder.decode(userModel.getPsw())));
            userModelMapper.insert(userModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkJaxwayServerAuthority(Integer jaxwayServerId, Integer userId) {
        List<JaxwayServerModel> jaxwayServerModels = jaxwayServerModelMapper.selectJaxwayServersByUserId(userId);
        boolean owntheServer = false;
        if(!CollectionUtils.isEmpty(jaxwayServerModels)){
            for(JaxwayServerModel jaxwayServerModel:jaxwayServerModels){
                if(jaxwayServerModel.getId().equals(jaxwayServerId)){
                    owntheServer = true;
                    break;
                }
            }
        }
        return owntheServer;
    }

    @Override
    public List<JaxwayServerModel> getJaxwayServersByUserId(Integer userId) {
        return jaxwayServerModelMapper.selectJaxwayServersByUserId(userId);
    }
}
