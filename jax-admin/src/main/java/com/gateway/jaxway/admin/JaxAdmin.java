package com.gateway.jaxway.admin;

import com.gateway.jaxway.admin.service.TestService;
import com.gateway.jaxway.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * @Author huaili
 * @Date 2019/4/3 18:58
 * @Description JaxAdmin启动类
 **/
@SpringBootApplication
@ComponentScan(value="com.gateway.jaxway")
public class JaxAdmin {
    @Autowired
    private Log log;

    @Autowired
    private TestService testService;

    @PostConstruct
    public void test(){
        log.log("测试");
        testService.dotest();
    }

    public static void main(String[] ags){
        SpringApplication.run(JaxAdmin.class);
    }
}
