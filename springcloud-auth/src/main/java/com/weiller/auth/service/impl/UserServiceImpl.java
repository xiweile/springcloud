package com.weiller.auth.service.impl;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.auth.entity.User;
import com.weiller.auth.repository.UserRepository;
import com.weiller.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements IUserService {

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

    @Override
    public User getByUsernameAndPassword(UserVo vo) {
        User user1 = new User();
        user1.setUsername(vo.getUsername());
        user1.setPassword(vo.getPassword());
        Example<User> template = Example.of(user1);
        return  userRepository.findOne(template).orElse(null);
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
