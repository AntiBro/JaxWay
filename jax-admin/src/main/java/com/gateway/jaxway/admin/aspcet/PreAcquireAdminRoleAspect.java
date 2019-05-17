package com.gateway.jaxway.admin.aspcet;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.dao.mapper.UserModelMapper;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.dao.support.RoleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.SESSION_USER_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/16 18:13
 * @Description PreAcquireAdminRoleAspect
 **/
@Aspect
@Component
public class PreAcquireAdminRoleAspect {

    @Autowired
    UserModelMapper userModelMapper;


    @Pointcut("@annotation(PreAcquireAdminRole)")
    public void requireAdminUser() {
    }

    @Around("requireAdminUser()")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {

        try {
            Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());

            Object[] args = pjp.getArgs();

            for(Object object:args){
                if(object instanceof ServerWebExchange){
                    ServerWebExchange serverWebExchange = (ServerWebExchange) object;
                    Integer userId = serverWebExchange.getSession().block().getAttribute(SESSION_USER_ID_KEY);
                    if(StringUtils.isEmpty(userId)){
                        logger.warn("userId from redis is null,please check login status");
                        return ResultVO.notAuthoried("无管理员权限");
                    }
                    UserModel userModel = userModelMapper.selectByPrimaryKey(userId);

                    if(userModel != null && userModel.getRoleType().equals(RoleType.ADMIN_USER.valueOf())){
                        Object returnValue = pjp.proceed();
                        return returnValue;
                    }
                }
            }


            return ResultVO.notAuthoried("无管理员权限");
        } catch (Throwable throwable) {
           // throwable.printStackTrace();
            throw throwable;
        }


    }
}
