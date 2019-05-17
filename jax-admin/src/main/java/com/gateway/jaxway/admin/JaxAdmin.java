package com.gateway.jaxway.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * @Author huaili
 * @Date 2019/4/3 18:58
 * @Description JaxAdmin启动类
 **/
@SpringBootApplication
@MapperScan("com.gateway.jaxway.admin.dao.mapper")
@EnableRedisWebSession(redisNamespace = "spring:${spring.profiles}:session",maxInactiveIntervalInSeconds = 6*60*60)
public class JaxAdmin {
    public static void main(String[] ags){
        SpringApplication.run(JaxAdmin.class);
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        //让springSession不再执行config命令,否则无法启动
        return ConfigureRedisAction.NO_OP;
    }
}
