package com.weiller.api.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "springcloud-auth")
public interface UserService {

    @GetMapping("/user/all")
    public Object getList();

    @GetMapping("/user/{id}")
    public Object get(@PathVariable("id") Integer id );

    @DeleteMapping("/user/{id}")
    public Object delete(@PathVariable("id") Integer id);


}
