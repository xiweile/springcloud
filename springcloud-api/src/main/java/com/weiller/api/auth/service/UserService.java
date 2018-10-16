package com.weiller.api.auth.service;

import com.weiller.api.auth.entity.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "springcloud-auth")
@Component
public interface UserService {

    @GetMapping("/user/all")
    public Object getList();

    @GetMapping("/user/{id}")
    public Object get(@PathVariable("id") Integer id );

    @DeleteMapping("/user/{id}")
    public Object delete(@PathVariable("id") Integer id);

    @PostMapping("/login")
    public Object login(@RequestBody UserVo user);
}
