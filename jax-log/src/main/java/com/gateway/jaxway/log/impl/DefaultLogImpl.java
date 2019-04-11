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
@Service
public class DefaultLogImpl implements Log {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void log(Object msg) {
        logger.info(msg.toString());
    }
}
