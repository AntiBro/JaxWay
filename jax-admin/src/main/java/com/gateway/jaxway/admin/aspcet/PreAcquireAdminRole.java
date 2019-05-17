package com.gateway.jaxway.admin.aspcet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author huaili
 * @Date 2019/5/16 18:11
 * @Description 检查是否有 管理员权限
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAcquireAdminRole {
}
