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
}
