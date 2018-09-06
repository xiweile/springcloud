package com.weiller.auth.hello.service;

import com.weiller.auth.hello.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 用户登陆信息类
 *
 */
public interface IUserService {

     Flux<User> findAll();

     Flux<User> getByIds(Flux<Integer> ids);

     Mono<User> getById(Integer id);

     Mono<User> createOrUpdate( User  user);

     Mono<User> delete(Integer id);

}
