package com.weiller.product.hello;

import com.weiller.api.auth.service.UserService;
import com.weiller.utils.model.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @RibbonClientName
    private String name;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public Object hello(){
        return new Msg("200","this is member app",null);
    }

    @DeleteMapping("/hello")
    public Object helloDel(){
        return new Msg("200","this is delete operation",null);
    }
/*
    @GetMapping("/hello/get")
    public Object consumerHello(){
        return restTemplate.getForObject("http://"+name+"/hello/get",Object.class);
    }*/

    @GetMapping("/remote/user/list")
    public Object getUser(){
        return userService.getList();
    }
}
