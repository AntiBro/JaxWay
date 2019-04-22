package com.gateway.jaxway.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huaili
 * @Date 2019/4/10 21:09
 * @Description TODO
 **/
@RestController
public class TestController {
//    @RequestMapping("/testflux")
//    public Mono<String> getTest(){
//        return Mono.just("测试安全过滤器");
//    }

    @RequestMapping("/testflux")
    public String getTest(){
        return "测试安全过滤器";
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
