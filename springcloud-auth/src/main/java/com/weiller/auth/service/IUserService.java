package com.weiller.auth.service;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.auth.entity.User;
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

     User getByUsernameAndPassword(UserVo user);

     Mono<User> createOrUpdate( User  user);

     Mono<User> delete(Integer id);

}
