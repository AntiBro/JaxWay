package com.gateway.jaxway.log;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author huaili
 * @Date 2019/4/3 18:11
 * @Description log代理类
 **/
@Service
@Primary
public class LogProxyService implements Log, InitializingBean, ApplicationContextAware {

    /**
     * 所有的 Log实现类
     */
    private List<Log> logs;

    private ApplicationContext applicationContext;

    private String LOG_PROXY_CLASS_NAME = "LogProxyService";

    @Override
    public void afterPropertiesSet() throws Exception {
        if(CollectionUtils.isEmpty(logs)) {
            // 从容器中获取所有的 实现类, 过滤掉自己
            logs =  applicationContext.getBeansOfType(Log.class).values().stream().filter(e-> !e.getClass().getName().endsWith(LOG_PROXY_CLASS_NAME)).collect(Collectors.toList());
            logs.stream().forEach(e->System.out.println("get jax-way log class:"+e.getClass().getName()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void log(Object msg, Object... params) {
        if(!CollectionUtils.isEmpty(logs)){
            logs.stream().forEach(e->e.log(msg,params));
        }
    }

    @Override
    public void log(LogType logType, Object msg, Object... params) {
        if(!CollectionUtils.isEmpty(logs)){
            logs.stream().forEach(e->e.log(logType,msg,params));
        }
    }
}
