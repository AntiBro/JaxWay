package com.gateway.jaxway.test.controller;

import com.gateway.common.beans.JaxClientAuthentication;
import com.gateway.common.beans.OpType;
import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author huaili
 * @Date 2019/4/10 21:09
 * @Description TODO
 **/
@RestController
public class TestController {

    @Autowired
    private Log log;
//    @RequestMapping("/test")
//    public Mono<String> getTestA(){
//        return Mono.just("测试安全过滤器");
//    }

    @RequestMapping("/testflux")
    public String getTest(){
        log.log("调用testflux");
        return "测试安全过滤器";
    }



    @RequestMapping("/testAuthor")
    public ResultVO getTestFor(){
        log.log("调用testflux");

        JaxClientAuthentication jaxAuthentication = new JaxClientAuthentication();
        jaxAuthentication.setOpType(OpType.ADD_APP);
        jaxAuthentication.setAppId("/test");
        jaxAuthentication.setPublishDate(new Date());
        jaxAuthentication.setPublisher("lili");

        List<JaxClientAuthentication> list = new ArrayList<>();
        for(int i=0;i<8;i++){
            list.add(jaxAuthentication);
        }
        return ResultVO.success(list);
    }


    @RequestMapping("/baidu")
    public ResultVO getTestSohu(){
        log.log("baidu");

        return ResultVO.success("baidu success");
    }
//
//    @GetMapping("/test")
//    public Mono<ResponseEntity<String>> proxy(ProxyExchange<String> proxy) throws Exception {
//        Mono<ResponseEntity<String>> responseEntityMono = proxy.uri("http://baidu.com").get();
////        System.out.println(responseEntityMono.doOnSuccess(e->{
////            System.out.println("内容");
////            System.out.println(e.getBody());
////        }));
//        return responseEntityMono;
//    }
}
