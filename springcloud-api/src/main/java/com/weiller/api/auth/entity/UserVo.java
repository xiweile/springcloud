package com.weiller.api.auth.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserVo {

    private Integer id;

    private String username;

    private String password;

    private Map<String,Object> info;

    private List<String> role;

}
