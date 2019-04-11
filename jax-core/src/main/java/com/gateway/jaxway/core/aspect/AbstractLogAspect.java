package com.gateway.jaxway.core.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huaili
 * @Description Controller日志切面抽象类,需要实现allPublicMethodCall()方法，指定相应 @Pointcut
 *
 */
public abstract class AbstractLogAspect {

    /**
     * 默认日志格式
     */
    protected static final String LOG_TEMPLATE = "Aspect: Api -> [{}] finished in {} ms, args: [{}]";

    protected static final String EXCEPTION_LOG_TEMPLATE = "Aspect  Api: exception occurred in {}. {}.";

    /**
     * 默认常用切面：PUBLIC METHOD CALL，实现类需要指定 @Pointcut
     */
    @Pointcut()
    protected abstract void allPublicMethodCall();

    /**
     * 返回api调用日志模版，更改模版样式需要 @Override 此方法
     */
    protected String getInternalApiCallInfoTemplate() {
        return LOG_TEMPLATE;
    }

    /**
     * 返回错误日志模版，更改模版样式需要 @Override 此方法
     */
    protected String getIllegalDetailsRequestExceptionTemplate() {
        return EXCEPTION_LOG_TEMPLATE;
    }

    protected String formatApiArgs(Object[] args, MethodSignature methodSignature) {
        String[] parameters = methodSignature.getParameterNames();
        if (parameters == null) {
            return "";
        }

        StringBuilder apiArgs = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            String parameter = parameters[i];
            Object arg = args[i];
            String argString = arg == null ? "" : arg.toString();
            apiArgs.append(parameter).append(" = ").append(argString);
            if (i < parameters.length - 1) {
                apiArgs.append(", ");
            }
        }
        return apiArgs.toString();
    }


    /**
     * 打印调用日志
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("allPublicMethodCall()")
    protected Object logDefaultFeignApiCallInfo(ProceedingJoinPoint pjp) throws Throwable {
        Object apiControllerCalled = pjp.getTarget();
        StringBuilder apiName = new StringBuilder();
        Class<?> targetClass = apiControllerCalled.getClass();
        Logger logger = LoggerFactory.getLogger(targetClass);
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        apiName.append(methodSignature.getName());
        try {
            long timeStart = System.currentTimeMillis();
            String apiArgs = formatApiArgs(pjp.getArgs(), methodSignature);
            Object returnValue = pjp.proceed();
            long timeUsed = System.currentTimeMillis() - timeStart;
            if (timeUsed >= 500) {
                logger.warn("SLOW API CALL " + getInternalApiCallInfoTemplate(), apiName.toString(), timeUsed, apiArgs);
            }
            logger.info(getInternalApiCallInfoTemplate(), apiName.toString(), timeUsed, apiArgs);
            return returnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }


}
