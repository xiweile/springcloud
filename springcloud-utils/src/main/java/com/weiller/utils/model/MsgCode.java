package com.weiller.utils.model;


/**
 * 消息码枚举类
 */
public enum  MsgCode {

    SUCCESS("200","响应成功"),
    ERROR("500","响应失败");


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
