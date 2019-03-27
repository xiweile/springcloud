package com.weiller.auth.social;

import com.weiller.api.auth.service.UserService;
import com.weiller.auth.user.entity.User;
import com.weiller.auth.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 社交认证注册
 *
 * @author weiller
 */
@Component
public class MyConnectionSignUp implements ConnectionSignUp {


    @Autowired
    IUserService iUserService;

    public String execute(Connection<?> connection) {

        String displayName = connection.getDisplayName();
        // 认证成功自动注册
        User user = new User();
        user.setUsername(displayName);
        user.setPassword("11111");
        iUserService.create(user);

        //根据社交用户信息，默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }
}