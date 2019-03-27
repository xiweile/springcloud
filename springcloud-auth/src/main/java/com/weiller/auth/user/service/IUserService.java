package com.weiller.auth.user.service;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.auth.user.entity.User;
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

     User create ( User  user);

     User createOrUpdate( User  user);

     void delete(Integer id);

     User getByUsername(String username);
}
