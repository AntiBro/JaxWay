package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.service.RoutesService;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.GatewayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.SESSION_USER_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/20 14:43
 * @Description SupportController
 **/
@RestController
@RequestMapping("/portal/support")
@CrossOrigin
// todo: 使用注解切面来验证 权限，以及登录状态
public class SupportController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoutesService routesService;

    @RequestMapping("/getPredicatesInfo")
    public ResultVO getPredicatesFactoryInfos(){
        return ResultVO.success(GatewayVO.getAllPredicatesInfo());
    }


    @RequestMapping("/getFilterInfos")
    public ResultVO getFilterInfos(){
        return ResultVO.success(GatewayVO.getAllFilterInfo());
    }


    // 根据登录的用户信息来获取 jaxway server 信息
    @RequestMapping("/getOwnJaxwayServers")
    public ResultVO getOwnJaxwayServers(ServerWebExchange exchange){
        Integer userId = exchange.getSession().block().getAttribute(SESSION_USER_ID_KEY);
        if(userId == null){
            return ResultVO.notAuthoried("登录失效,请重新登录");
        }
        return ResultVO.success(userService.getJaxwayServersByUserId(userId));
    }


    // 根据 jaxway server id 和 userinfo 信息来判断 jaxway server 的归属权限
    @RequestMapping("/getOwnJaxwayServersRouteDefinitions")
    public ResultVO getOwnJaxwayServersRouteDefinitions(Integer jaxwayServerId,ServerWebExchange exchange){
        Integer userId = exchange.getSession().block().getAttribute(SESSION_USER_ID_KEY);
        if(userId == null){
            return ResultVO.notAuthoried("登录失效,请重新登录");
        }

        if(userService.checkJaxwayServerAuthority(jaxwayServerId,userId)) {
            return ResultVO.success(routesService.getTotalRoutesInfo(jaxwayServerId,-1, RoutesService.RouteType.FINAL));
        }

        return ResultVO.notAuthoried("无权限");
    }

}
