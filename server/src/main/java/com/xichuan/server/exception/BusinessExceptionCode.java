package com.xichuan.server.exception;

public enum BusinessExceptionCode {

    USER_PASSWORD_NOT_EXIST("密码不能为空"),
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    LOGIN_IMAGECODE_ERROR("验证码不能为空"),
    LOGIN_MEMBER_ERROR("手机号不存在或密码错误"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
