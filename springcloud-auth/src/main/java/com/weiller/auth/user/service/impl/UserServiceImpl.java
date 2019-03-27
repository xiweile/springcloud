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

    @Autowired
    private  PasswordEncoder passwordEncoder;

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

    public  User create ( User  user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insertSelective(user);
        return  user ;
    }

    public  User createOrUpdate( User  user){
        User user1 = userMapper.getByUsername(user.getUsername());
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if(user1!=null){
            user.setId(user1.getId());
            userMapper.updateByPrimaryKeySelective(user);
        }else{
            userMapper.insertSelective(user);
        }
        return  user ;
    }

    public void delete(Integer id){
        userMapper.deleteByPrimaryKey(id) ;
    }
}
