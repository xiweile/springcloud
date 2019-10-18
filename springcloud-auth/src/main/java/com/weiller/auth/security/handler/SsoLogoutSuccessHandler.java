package com.weiller.auth.security.handler;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.apache.HttpClientUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SsoLogoutSuccessHandler implements LogoutHandler {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        //restTemplate.getForEntity("http://localhost:8003/logout",Object.class);
        //restTemplate.getForEntity("http://localhost:8002/logout",Object.class);
        System.out.println("sso 已经退出了");
    }
}
