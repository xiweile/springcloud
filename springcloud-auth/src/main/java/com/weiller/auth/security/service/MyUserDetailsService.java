package com.weiller.auth.security.service;

import com.weiller.auth.user.dao.UserMapper;
import com.weiller.auth.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.weiller.auth.user.entity.User userObj = userMapper.getByUsername(username);

        return new User( userObj.getUsername(),
                userObj.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList((String[])userObj.getRoles().toArray()));

    }
}
