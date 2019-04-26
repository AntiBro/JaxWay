package com.gateway.jaxway.admin.controller;

import com.gateway.jaxway.core.authority.JaxwayTokenCoder;
import com.gateway.jaxway.core.common.JaxwayConstant;
import com.gateway.jaxway.core.utils.http.JaxAuthentication;
import com.gateway.jaxway.core.utils.http.OpType;
import com.gateway.jaxway.core.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    private JaxwayTokenCoder jaxwayTokenCoder;

    @RequestMapping("/getAppInfo")
    public ResultVO getTestAppInfo(String appid) throws UnsupportedEncodingException {
        log.debug("getAppInfo appid={}",appid);

        JaxAuthentication jaxAuthentication = new JaxAuthentication();
        jaxAuthentication.setOpType(OpType.ADD);
        jaxAuthentication.setUrl("/test");
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");
        jaxAuthentication.setToken(jaxwayTokenCoder.encode("123456789"));

        System.out.println(jaxAuthentication.getToken());

        if(appid.equals("test2")){
            return ResultVO.success(JaxAuthentication.NONE);
        }
        return ResultVO.success(jaxAuthentication);
    }
}
