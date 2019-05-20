package com.gateway.jaxway.admin.aspcet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author huaili
 * @Date 2019/5/20 16:05
 * @Description 检查
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreCheckJaxwayOwnAuthority {
}
