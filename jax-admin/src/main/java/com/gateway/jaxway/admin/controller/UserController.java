package com.gateway.jaxway.admin.controller;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.aspcet.PreAcquireAdminRole;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.service.UserService;
import com.gateway.jaxway.admin.vo.UserInfoVO;
import com.gateway.jaxway.admin.vo.UserRequestParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import static com.gateway.jaxway.admin.support.JaxAdminConstant.SESSION_USER_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/15 16:40
 * @Description UserController
 **/
@RestController
@CrossOrigin
@RequestMapping("/portal")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody UserRequestParamsVO userRequestParamsVO, ServerWebExchange exchange){

       // HttpSession session = exchange.getSession().;

        UserModel userModel = userService.checkUser(userRequestParamsVO.getUsername(), userRequestParamsVO.getPwd());
        if(userModel != null){
            exchange.getSession().subscribe(session ->{
               session.getAttributes().put(SESSION_USER_ID_KEY,userModel.getId());
            });
            return ResultVO.success(userModel);
        }
        return ResultVO.notAuthoried("登录失败,请检查用户名和密码");
    }


    @PostMapping("/checkAuthority")
    public ResultVO checkAuthority(String username,String path){
        return ResultVO.success("登录成功");
    }




    @PostMapping("/logout")
    public ResultVO logout( ServerWebExchange exchange){
        WebSession session = exchange.getSession().block();
        if( !session.isExpired()){
            log.info("ttl ={}ms",session.getMaxIdleTime().get(ChronoUnit.SECONDS));
            session.setMaxIdleTime(Duration.ofSeconds(10));
            session.invalidate();
            session.changeSessionId();
        }
        return ResultVO.success("登出成功");
    }
}
