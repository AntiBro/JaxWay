package com.gateway.jaxway.admin.vo;

import com.gateway.jaxway.admin.dao.model.UserModel;
import com.gateway.jaxway.admin.dao.support.RoleType;
import org.springframework.core.style.ToStringCreator;

/**
 * @Author huaili
 * @Date 2019/5/16 21:08
 * @Description UserInfoVO
 **/
public class UserInfoVO {
    private String username;
    private String psw;
    private String email;
    private String avatar;

    private Integer roleType = RoleType.COMMON_USER.valueOf();

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserModel transferToUserModel(){
        UserModel userModel = new UserModel();
        userModel.setUserName(getUsername());
        // base64.encode("jax-way"+username+psw)
        userModel.setPsw(getPsw());
        userModel.setEmail(getEmail());
        userModel.setAvatar(getAvatar());
        userModel.setRoleType(getRoleType());
        return userModel;
    }

    public UserModel transferToCommonUserModel(){
        UserModel userModel = transferToUserModel();
        userModel.setRoleType(RoleType.ADMIN_USER.valueOf());
        return userModel;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("username",getUsername())
                .append("psw",getPsw())
                .append("email",getEmail())
                .append("roleType",getRoleType())
                .toString();
    }
}
