package com.weiller.auth.user.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class User {
    private Integer id;

    private String password;

    private String username;

    private String state;

    private Map<String,Object> info;

    private List<String> roles;

}