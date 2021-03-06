package com.weiller.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class ClientWebsecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Value("${auth-server}")
    private String authServer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl(authServer+"/exit");
        http.antMatcher("/**").authorizeRequests()
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
