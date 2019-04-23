package com.gateway.jaxway.test.aspcet;
import com.gateway.jaxway.log.Log;
import org.springframework.stereotype.Component;

/**
 * @Author huaili
 * @Date 2019/4/22 15:37
 * @Description 自定义的 log实现
 **/
@Component
public class Mylog implements Log {
    @Override
    public void log(Object msg, Object... params) {
        System.out.println("自定义的实现 log 实现 msg="+msg.toString());
    }

    @Override
    public void log(LogType logType, Object msg, Object... params) {

    }
}
