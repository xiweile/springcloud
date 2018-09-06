package com.weiller.auth.hello.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    @Transient
    private Map<String,Object> info;

    @Transient
    private List<String> role;


}
