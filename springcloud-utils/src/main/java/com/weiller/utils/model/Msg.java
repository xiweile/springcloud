package com.weiller.utils.model;

import lombok.Data;

@Data
public class Msg {

    private String code;

    private String msg;

    private Object data;

    public Msg() {
    }

    public Msg(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Msg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public  static Msg success(Object data){
        return new Msg("200","响应成功",data);
    }

    public  static Msg failure(String code,String msg){
        return new Msg(code,msg,null);
    }
}
