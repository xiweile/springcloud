package com.weiller.identity.utils.model;


/**
 * 消息码枚举类
 */
public enum  MsgCode {

    SUCCESS("200","响应成功"),
    ERROR("500","响应失败"),

    AUTH_INVALID("10001","身份验证无效"),

    AUTH_LOGIN_ERROR("10003","用户不存在或密码错误");

    private String code;

    private String desc;

    MsgCode() {

    }
    MsgCode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
