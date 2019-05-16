package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.UserRequestParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huaili
 * @Date 2019/5/15 16:40
 * @Description UserController
 **/
@RestController
@CrossOrigin
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody UserRequestParamsVO userRequestParamsVO){

        UserModel userModel = userService.checkUser(userRequestParamsVO.getUsername(), userRequestParamsVO.getPwd());
        if(userModel != null){
            return ResultVO.success(userModel);
        }
        return ResultVO.notAuthoried("登录失败,请检查用户名和密码");
    }


    @PostMapping("/checkAuthority")
    public ResultVO checkAuthority(String username,String path){
        return ResultVO.success("登录成功");
    }

}
