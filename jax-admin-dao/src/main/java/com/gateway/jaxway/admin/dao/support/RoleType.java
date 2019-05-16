package com.gateway.jaxway.admin.dao.support;

/**
 * @Author huaili
 * @Date 2019/5/16 17:40
 * @Description RoleType
 **/
public enum RoleType {
    COMMON_USER(0),
    ADMIN_USER(1);

    private int roleType;
    RoleType(int roleType){
        this.roleType = roleType;
    }

    public int valueOf(){
        return this.roleType;
    }
}
