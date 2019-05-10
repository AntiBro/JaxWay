package com.gateway.jaxway.admin.controller;

import com.gateway.common.JaxwayCoder;
import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Author huaili
 * @Date 2019/4/24 20:39
 * @Description AppInfoController
 **/
@RestController
public class AppInfoController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JaxwayCoder jaxwayCoder;

    @RequestMapping("/getAppInfo")
    public ResultVO getTestAppInfo(String appid, ServerWebExchange exchange) throws UnsupportedEncodingException {


        log.info("getAppInfo appid={} remoteIP={}",appid, IpUtil.getIpAddr(exchange.getRequest()));


        JaxClientAuthentication jaxAuthentication = new JaxClientAuthentication();
        String appId = "test";
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId(jaxwayCoder.encode(appId));
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setToken(jaxwayCoder.encode("/sohu/**"));


        if(appid.equals("test2")){
            return ResultVO.success(JaxClientAuthentication.NONE);
        }
        return ResultVO.success(jaxAuthentication);
    }
}
