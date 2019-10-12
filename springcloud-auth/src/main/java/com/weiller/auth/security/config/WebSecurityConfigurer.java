package com.weiller.auth.security.config;

import com.weiller.auth.security.handler.FailureAuthenticationHandler;
import com.weiller.auth.security.handler.SsoLogoutSuccessHandler;
import com.weiller.auth.security.handler.SuccessAuthenticationHandler;
import com.weiller.auth.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import java.util.Locale;

/**
 * 定义用户ID、密码和角色
 */
@Configuration
@Order(1)
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

    @Autowired
    private SsoLogoutSuccessHandler ssoLogoutSuccessHandler;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    MessageSource messageSource;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**","/**/*.js","/**/*.css","/**/*.jpg","/**/*.png","/**/*.woff2");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/login","/oauth/**","/online",
                            "/register",
                            "/socialRegister",  //社交账号注册和绑定页面
                            "/user/register",   //处理社交注册请求
                            "/social/info"     //获取当前社交用户信息
                     ).permitAll()
                    .antMatchers("/user/all").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                 /*   .successHandler(successHandler)
                    .failureHandler(failureHandler)*/
                    .permitAll()
                .and()
                .apply(merryyouSpringSocialConfigurer)
                .and()
                .logout().addLogoutHandler(ssoLogoutSuccessHandler)
                .and()
                .csrf().disable()
                ;
    }

    /**
     * 去除角色中role_的前缀
     * 表达式需要.access("hasRole('ADMIN')");
     *
     * @throws Exception
     */
   /* @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }*/


}
