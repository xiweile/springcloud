package com.weiller.auth.security.service;

import com.alibaba.fastjson.JSONObject;
import com.weiller.auth.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyUserDetailsService implements UserDetailsService,SocialUserDetailsService{

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        if("".equals(username.trim())){
            throw new UsernameNotFoundException(this.messages.getMessage("LdapAuthenticationProvider.emptyUsername"));
        }
        com.weiller.auth.user.entity.User userObj = userMapper.getUserAndRoleByUsername(username);
        if(userObj!=null){
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
        }else {
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[] { username }, "Username {0} not found"));
        }

    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserDetails userDetails = this.loadUserByUsername(userId);
        return new SocialUser(userDetails.getUsername(),userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}
