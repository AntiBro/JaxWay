package com.gateway.jaxway.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author huaili
 * @Date 2019/4/10 20:30
 * @Description 测试类
 **/
@SpringBootApplication
//@ComponentScan(value="com.gateway.jaxway")
//@ComponentScan(basePackages = {"org.springframework.http.codec"})
public class JaxTestMain {
    public static void main(String[] ags){
        SpringApplication.run(JaxTestMain.class);
    }
//
//    @Bean
//    ServerCodecConfigurer getServerCodecConfigurer(){
//        return new DefaultServerCodecConfigurer();
//    }

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/baidu")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("https://www.baidu.com"))
//                .build();
//    }

}
