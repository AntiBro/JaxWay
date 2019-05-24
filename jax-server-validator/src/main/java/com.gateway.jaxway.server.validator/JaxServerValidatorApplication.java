package com.gateway.jaxway.server.validator;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author huaili
 * @Date 2019/5/6 18:39
 * @Description JaxServerValidatorApplication
 **/

@SpringBootApplication(scanBasePackages={"com.gateway.jaxway.server.validator"})
public class  JaxServerValidatorApplication {

    public static void main(String[] ags) {
       // SpringApplication.run(JaxServerValidatorApplication.class);
        new SpringApplicationBuilder(JaxServerValidatorApplication.class).
                profiles("dev").
                web(WebApplicationType.REACTIVE).sources(JaxServerValidatorApplication.class).
                properties("spring.application.name=213123").
                properties("server.port=8989").
                build().
                run();
    }
}
