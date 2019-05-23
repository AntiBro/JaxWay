package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.OpType;
import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.aspcet.PreAcquireAdminRole;
import com.gateway.jaxway.admin.aspcet.PreCheckJaxwayOwnAuthority;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.service.RoutesService;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import java.net.URISyntaxException;

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
    @Autowired
    private RoutesService routesService;


    @PreAcquireAdminRole
    @PostMapping("/insertUser")
    public ResultVO insertUser(@RequestBody UserInfoVO userInfoVO, ServerWebExchange exchange){
        userService.insertUser(userInfoVO);
        return ResultVO.success("插入成功");
    }


    @PreCheckJaxwayOwnAuthority
    @PostMapping("/insertRouteDefinition")
    public ResultVO insertRouteDefinition(@RequestBody JaxwayRouteModel jaxwayRouteModel,ServerWebExchange exchange) throws URISyntaxException {
        routesService.insertRouteDefinition(jaxwayRouteModel);
        return ResultVO.success("插入成功");
    }


    // delete route definition,check the jaxway server own authority
    @PreCheckJaxwayOwnAuthority
    @PostMapping("/removeRouteDefinition")
    public ResultVO removeRouteDefinition(@RequestBody JaxwayRouteModel jaxwayRouteModel,ServerWebExchange exchange) throws URISyntaxException {
       // change to delete_route Optype
        jaxwayRouteModel.setOpType(OpType.DELETE_ROUTE.ordinal());
        routesService.insertDeleRouteDefinition(jaxwayRouteModel);
        return ResultVO.success("删除成功");
    }

}
