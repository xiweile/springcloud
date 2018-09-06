package com.weiller.auth.hello.controller;

import com.weiller.auth.hello.entity.User;
import com.weiller.auth.hello.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;


    @PostMapping("/user")
    public Mono<User> create(@RequestBody User   user ) {
        return this.iUserService.createOrUpdate(user);
    }

    @GetMapping("/user/all")
    public Flux<User> getList() {
        return this.iUserService.findAll();
    }

    @GetMapping("/user/{id}")
    public Mono<User> get(@PathVariable("id") Integer id ) {
        return this.iUserService.getById(id);
    }

    @DeleteMapping("/user/{id}")
    public Mono<Tuple2> delete(@PathVariable("id")Integer id){
        Tuple2<Boolean, String> tuple2 = Tuples.of(true, "删除成功！");
        return Mono.just(tuple2);
    }


}
