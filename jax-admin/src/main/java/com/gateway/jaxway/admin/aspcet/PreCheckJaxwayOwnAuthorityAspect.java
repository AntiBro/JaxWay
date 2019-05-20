package com.gateway.jaxway.admin.aspcet;

import com.gateway.common.beans.ResultVO;
import com.gateway.jaxway.admin.dao.mapper.JaxwayServerModelMapper;
import com.gateway.jaxway.admin.dao.model.JaxwayRouteModel;
import com.gateway.jaxway.admin.dao.model.JaxwayServerModel;
import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.dao.support.RoleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

import static com.gateway.jaxway.admin.support.JaxAdminConstant.SESSION_USER_ID_KEY;

/**
 * @Author huaili
 * @Date 2019/5/20 16:06
 * @Description PreCheckJaxwayOwnAuthorityAspect
 **/
@Aspect
@Component
public class PreCheckJaxwayOwnAuthorityAspect {
    public static String JAX_WAY_SERVER_ID_KEY = "jaxwayServerId";

    @Autowired
    private JaxwayServerModelMapper jaxwayServerModelMapper;

    @Pointcut("@annotation(PreCheckJaxwayOwnAuthority)")
    public void checkJaxwayOwnAuthority() {
    }

    @Around("checkJaxwayOwnAuthority()")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
            Object[] args = pjp.getArgs();
            Integer jaxwayServerId = new Integer(0);
            for(Object object:args){

                if (object instanceof JaxwayRouteModel) {
                    JaxwayRouteModel jaxwayRouteModel = (JaxwayRouteModel)object;
                    jaxwayServerId = jaxwayRouteModel.getJaxwayServerId();
                    if(jaxwayServerId == null){
                        logger.warn("PreCheckJaxwayOwnAuthorityAspect jaxServerId in Aop cannot be null");
                        return ResultVO.notAuthoried("jaxServerId in Aop cannot be null");
                    }
                    continue;
                }

                if(object instanceof ServerWebExchange){
                    ServerWebExchange serverWebExchange = (ServerWebExchange) object;
                    Integer userId = serverWebExchange.getSession().block().getAttribute(SESSION_USER_ID_KEY);
                    if(StringUtils.isEmpty(userId)){
                        logger.warn("userId from redis is null,please check login status");
                        return ResultVO.notAuthoried("登录失效，请重新登录");
                    }
                    List<JaxwayServerModel> jaxwayOwnLists = jaxwayServerModelMapper.selectJaxwayServersByUserId(userId);
                    if(!CollectionUtils.isEmpty(jaxwayOwnLists)){
                        for(JaxwayServerModel jaxwayServerModel:jaxwayOwnLists){
                            if(jaxwayServerModel.getId().equals(jaxwayServerId)){
                                return pjp.proceed();
                            }
                        }
                    }
                }
            }
            logger.warn("PreCheckJaxwayOwnAuthorityAspect find ilegal request");
            return ResultVO.notAuthoried("无管理员权限");
        } catch (Throwable throwable) {
            // throwable.printStackTrace();
            throw throwable;
        }

    }
}
