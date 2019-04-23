package com.gateway.jaxway.test;

import com.gateway.jaxway.core.utils.http.HttpUtil;
import com.gateway.jaxway.core.utils.http.JaxHttpRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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

//        JaxHttpRequest jaxHttpRequest = JaxHttpRequest.newBuilder().requestUrl("http://10.16.57.182:8011").build();
//
//        HttpUtil httpUtil = HttpUtil.newInstance();
//        int n = 20;
//        long startTime = System.currentTimeMillis();
//        List<JaxHttpRequest> list =new ArrayList<>();
//        for(int i=0;i<n;i++){
//            list.add(jaxHttpRequest);
//        }
//        list.parallelStream().forEach(e->{
//            httpUtil.doGet(e,String.class);
//        });
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("cost time="+(endTime-startTime));



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
