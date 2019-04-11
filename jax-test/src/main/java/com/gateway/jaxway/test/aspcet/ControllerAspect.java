package com.gateway.jaxway.test.aspcet;

import com.gateway.jaxway.core.aspect.AbstractLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author huaili
 * @Date 2019/4/10 21:24
 * @Description Aspect
 **/
@Aspect
@Component
public class ControllerAspect extends AbstractLogAspect {


    /**
     * 定义切面 位置
     */
    @Pointcut("execution(public * com.gateway.jaxway.test.controller.*.*(..))")
    @Override
    protected void allPublicMethodCall() {
        // no need to implement this pointcut
    }

    /**
     * 定义模板
     * @return
     */
    @Override
    protected String getInternalApiCallInfoTemplate() {
        return "Controller "+LOG_TEMPLATE;
    }
}
