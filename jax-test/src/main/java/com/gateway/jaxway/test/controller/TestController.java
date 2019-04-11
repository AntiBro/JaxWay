package com.gateway.jaxway.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Author huaili
 * @Date 2019/4/10 21:09
 * @Description TODO
 **/
@RestController
public class TestController {
    @RequestMapping("/testflux")
    public Mono<String> getTest(){
        return Mono.just("测试安全过滤器");
    }
}
