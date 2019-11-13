package com.weiller.product.hello;

import com.weiller.api.auth.service.UserService;
import com.weiller.utils.model.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @RibbonClientName
    private String name;

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    UserService userService;


    @GetMapping("/hello")
    public Object hello(){
        return new Msg("200","this is public product  app",null);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/hello")
    public Object helloDel(){
        return new Msg("200","this is delete operation",null);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/hello/a")
    public Object helloA(){
        return new Msg("200","我是a资源",null);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/hello/b")
    public Object helloB(){
        return new Msg("200","我是b资源",null);
    }
    /*
    @GetMapping("/hello/get")
    public Object consumerHello(){
        return restTemplate.getForObject("http://"+name+"/hello/get",Object.class);
    }*/

    @GetMapping("/remote/user/list")
    public Object getUser(){
       // return userService.getList();
        log.info("accessToken:{}",oAuth2RestTemplate.getAccessToken());
        return  oAuth2RestTemplate.getForEntity("http://localhost:8001/auth/user/all", String.class);
    }

    @GetMapping("/remote/online")
    public Object isOnline(){
        return userService.isOnline();
    }
}
