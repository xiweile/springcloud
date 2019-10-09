package com.weiller.api.auth.service;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.utils.model.Msg;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@FeignClient(value = "springcloud-auth",path = "auth")
@Component
public interface UserService {

    @GetMapping("/user/all")
    Object getList();

    @GetMapping(value = "/user/{id}",produces = "application/json;charset=UTF-8")
    Object get(@PathVariable("id") Integer id );

    @DeleteMapping("/user/{id}")
    Object delete(@PathVariable("id") Integer id);

    @PostMapping("/user/login")
    Object login(@RequestBody UserVo user);

    @GetMapping("/online")
    Msg isOnline();
}
