package com.gateway.jaxway.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author huaili
 * @Date 2019/4/3 18:58
 * @Description JaxAdmin启动类
 **/
@SpringBootApplication
public class JaxAdmin {
//    @Autowired
//    private Log log;
//
//    @Autowired
//    private TestService testService;
//
//    @PostConstruct
//    public void test(){
//        log.log("测试");
//        testService.dotest();
//    }

    public static void main(String[] ags){
        SpringApplication.run(JaxAdmin.class);
    }
}
