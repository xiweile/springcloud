package com.weiller.springcloudprivider.hello.entity;

import lombok.Data;

@Data
public class Msg {

    private String code;

    private boolean isSuccess;

    private String msg;

    private Object obj;
}
