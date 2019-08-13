package com.weiller.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.logging.Filter;

/**
 * 定义用户访问时要通过验证
 */
//@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    @Override
    public void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE,"/hello").hasRole("ADMIN")
                .anyRequest().authenticated();*/
    }

}
