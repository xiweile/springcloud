package com.weiller.auth.security.config;

import com.weiller.auth.security.handler.FailureAuthenticationHandler;
import com.weiller.auth.security.handler.SuccessAuthenticationHandler;
import com.weiller.auth.social.MerryyouSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 定义用户ID、密码和角色
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SpringSocialConfigurer merryyouSpringSocialConfigurer;

    @Autowired
    private FailureAuthenticationHandler failureHandler;

    @Autowired
    private SuccessAuthenticationHandler successHandler;

/*    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }*/

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhangsan")
                .password(new BCryptPasswordEncoder().encode("zhangsan"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN","USER");

    }*/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/login","/login/*",
                            "/register",
                            "/social/info",
                            "/**/*.js",
                            "/**/*.css",
                            "/**/*.jpg",
                            "/**/*.png",
                            "/**/*.woff2").permitAll()
                    .antMatchers("/user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                   /* .successHandler(successHandler)
                    .failureHandler(failureHandler)*/
                    .permitAll()
                .and()
                .apply(merryyouSpringSocialConfigurer)
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                ;
    }

}
