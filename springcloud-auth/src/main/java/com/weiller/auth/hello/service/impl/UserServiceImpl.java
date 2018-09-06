package com.weiller.auth.hello.service.impl;

import com.weiller.auth.hello.entity.User;
import com.weiller.auth.hello.repository.UserRepository;
import com.weiller.auth.hello.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll(){
        return Flux.fromIterable(userRepository.findAll());
    }

    public Flux<User> getByIds(Flux<Integer> ids){
        return ids.flatMap(id -> Mono.justOrEmpty(userRepository.findById(id)));
    }

    public Mono<User> getById(Integer id){
        return Mono.justOrEmpty(userRepository.findById(id));
    }

    public Mono<User> createOrUpdate( User  user){
        User save = userRepository.save(user);
        return Mono.just(save);
    }

    public Mono<User> delete(Integer id){
        User user = new User();
        user.setId(id);
        userRepository.delete( user);
        return Mono.justOrEmpty(user);
    }
}
