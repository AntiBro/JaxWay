package com.gateway.jaxway.log;

/**
 * @Author huaili
 * @Date 2019/4/3 17:53
 * @Description 用于不同方式的 日志处理
 **/
public interface Log {

    void log(Object msg,Object ... params);

    void log(LogType logType,Object msg,Object... params);


    enum LogType{
        TRACE,
        DEBUG,
        INFO,
        ERROR,
        WARN;
    }
}
