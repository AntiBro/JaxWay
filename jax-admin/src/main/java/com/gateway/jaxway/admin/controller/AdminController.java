package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.aspcet.PreAcquireAdminRole;
import com.gateway.jaxway.admin.aspcet.PreCheckJaxwayOwnAuthority;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Author huaili
 * @Date 2019/5/17 16:26
 * @Description AdminController
 **/
@RestController
@RequestMapping("/portal")
@CrossOrigin
public class AdminController extends BaseController {


    @Autowired
    private UserService userService;


    @PreAcquireAdminRole
    @PostMapping("/insertUser")
    public ResultVO insertUser(@RequestBody UserInfoVO userInfoVO, ServerWebExchange exchange){
        userService.insertUser(userInfoVO);
        return ResultVO.success("插入成功");
    }


    @PreCheckJaxwayOwnAuthority
    @PostMapping("/insertRouteDefinition")
    public ResultVO insertRouteDefinition(@RequestBody JaxwayRouteModel jaxwayRouteModel,ServerWebExchange exchange){
        return ResultVO.success("插入成功");
    }

}
