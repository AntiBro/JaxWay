package com.gateway.jaxway.log.impl;

import com.gateway.jaxway.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author huaili
 * @Date 2019/4/3 18:48
 * @Description default log service for jax-way
 **/
public class DefaultLogImpl implements Log {
    Logger logger = LoggerFactory.getLogger(getClass());

    public DefaultLogImpl(){

    }
    public DefaultLogImpl(Class<?> clazz){
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void log(Object msg, Object... params) {
        log(LogType.INFO,msg,params);
    }

    @Override
    public void log(LogType logType, Object msg, Object... params) {
        switch (logType) {
            case TRACE:
                logger.trace(msg.toString(), params);
                break;
            case DEBUG:
                logger.debug(msg.toString(), params);
                break;
            case INFO:
                logger.info(msg.toString(), params);
                break;
            case ERROR:
                logger.error(msg.toString(), params);
                break;
            case WARN:
                logger.warn(msg.toString(), params);
                break;
            default:
                logger.info(msg.toString(), params);
                break;

        }
    }
}
