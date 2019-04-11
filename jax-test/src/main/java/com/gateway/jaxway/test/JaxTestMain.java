package com.gateway.jaxway.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author huaili
 * @Date 2019/4/10 20:30
 * @Description 测试类
 **/
@SpringBootApplication
@ComponentScan(value="com.gateway.jaxway")
public class JaxTestMain {
    public static void main(String[] ags){
        SpringApplication.run(JaxTestMain.class);
    }
}
