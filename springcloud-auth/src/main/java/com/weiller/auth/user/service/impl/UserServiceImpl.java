package com.weiller.auth.user.service.impl;

import com.weiller.api.auth.entity.UserVo;
import com.weiller.auth.user.dao.UserMapper;
import com.weiller.auth.user.entity.User;
import com.weiller.auth.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("iUserService")
public class UserServiceImpl implements IUserService {



    private  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserMapper userMapper;

    public Flux<User> findAll(){
        return Flux.fromIterable(userMapper.findAll());
    }

    public Flux<User> getByIds(Flux<Integer> ids){
        return ids.flatMap(id -> Mono.justOrEmpty(userMapper.selectByPrimaryKey(id)));
    }

    public Mono<User> getById(Integer id){
        return Mono.justOrEmpty(userMapper.selectByPrimaryKey(id));
    }

    public User getByUsername(String username){
        return  userMapper.getByUsername(username) ;
    }

    @Override
    public User getByUsernameAndPassword(UserVo vo) {
        return  userMapper.getByUsernameAndPassword(vo.getUsername(),vo.getPassword());
    }

    public Mono<User> createOrUpdate( User  user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        User user1 = userMapper.getByUsername(user.getUsername());
        if(user1!=null){
            userMapper.insertSelective(user);
        }else{
            userMapper.updateByPrimaryKeySelective(user);
        }
        return Mono.just(user);
    }

    public void delete(Integer id){
        userMapper.deleteByPrimaryKey(id) ;
    }
}
