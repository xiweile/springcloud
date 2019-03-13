package com.weiller.auth.security.service;

import com.weiller.auth.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.weiller.auth.user.entity.User userObj = userMapper.getUserAndRoleByUsername(username);
        List<GrantedAuthority>   authorityList=new ArrayList<>();
        List<String> stringList = userObj.getRoles();
        if(stringList!=null) {
            String[] strings = new String[stringList.size()];
             authorityList = AuthorityUtils.createAuthorityList(stringList.toArray(strings) );
        }
        User user = new User(userObj.getUsername(),
                userObj.getPassword(),
                true,
                true,
                true,
                true,authorityList
                );
        return user;

    }
}
