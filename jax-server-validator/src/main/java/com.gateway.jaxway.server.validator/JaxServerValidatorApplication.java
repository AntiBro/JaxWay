package com.gateway.jaxway.server.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Author huaili
 * @Date 2019/5/6 18:39
 * @Description JaxServerValidatorApplication
 **/

@SpringBootApplication(scanBasePackages={"com.gateway.jaxway.server.validator"},
        exclude = {org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration.class,
                org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration.class})
public class  JaxServerValidatorApplication implements EnvironmentPostProcessor {
    enum PortMode{
        FROM_PROPERTIES,
        FROM_INNER_GATEWAY;
    }
    private static PortMode DEFAULT_PORT_MODE = PortMode.FROM_PROPERTIES;

    private static int INNER_BASE_GATE_WAY_PORT = 9000;

    private static ThreadLocal<String> springBootApplicationName = new ThreadLocal<>();

    public static PortMode getPortMode(){
        return DEFAULT_PORT_MODE;
    }

    public static void changerPortModeToInnerGateway(){
        DEFAULT_PORT_MODE = PortMode.FROM_INNER_GATEWAY;
    }

    public static void setSpringBootApplicationName(String applicationName){
        springBootApplicationName.set(applicationName);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //必须在SystemProperties 中设置server.port 的值否则会使用 配置文件的 port 导致无法启动 springboot 应用
        if(DEFAULT_PORT_MODE == PortMode.FROM_INNER_GATEWAY) {
            environment.getSystemProperties().put("spring.application.name",springBootApplicationName.get());
            environment.getSystemProperties().put("server.port",getAvailablePort(INNER_BASE_GATE_WAY_PORT));
        }
    }

    private int getAvailablePort(int port) {
        try {
            SpringApplicationAdminJmxAutoConfiguration springApplicationAdminJmxAutoConfiguration;
            ServerSocket socket = new ServerSocket(port);
            socket.close();
            return port;
        } catch (IOException e) {
            return getAvailablePort(port + 1);
        }
    }
}
