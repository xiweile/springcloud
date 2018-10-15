package com.weiller.product.hello;

import com.weiller.api.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
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

    @GetMapping("/hello/get")
    public Object consumerHello(){
        return restTemplate.getForObject("http://"+name+"/hello/get",Object.class);
    }

    @GetMapping("/remote/user/list")
    public Object getUser(){
        return userService.getList();
    }
}
