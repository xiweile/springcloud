package com.weiller.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/*
 * 认证服务的定义
 */
@EnableAuthorizationServer
@Configuration
public class OAuth2AuthConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    AuthenticationManager authenticationManagerBean;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    TokenStore redisTokenStore;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")

        ;
    }

    /*
     * 那些客户将注册到服务
     * @param clients
     * @throws Exception
    */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 /*       clients.inMemory()
                .withClient("product")
                .redirectUris("http://localhost:8002/login")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code","refresh_token") //,"password","client_credentials"
                .scopes("webclient","mobileclient")
                .autoApprove(true)//自动授权
        .and()
                .withClient("member")
                .redirectUris("http://localhost:8003/login")
                .secret(new BCryptPasswordEncoder().encode("123456"))//,"password","client_credentials"
                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("webclient","mobileclient")
                .autoApprove(true)
        ;*/
        clients.jdbc(dataSource);

    }

    /*
     * 定义使用的组件-使用spring提供的默认验证管理器和用户详细信息服务
     * @param endpoints
     * @throws Exception
      */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //endpoints.accessTokenConverter(jwtAccessTokenConverter()); // 用于jwt
        endpoints.tokenStore(redisTokenStore )
                .authenticationManager(authenticationManagerBean)
                //.tokenEnhancer(new CustomTokenEnhancer())
        ;
    }

    @Bean
    public TokenStore redisTokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);//redis存储access_token
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore );
        tokenServices.setSupportRefreshToken(true);
        //tokenServices.setClientDetailsService(clientDetails());
        // token有效期自定义设置，默认1 小时
        tokenServices.setAccessTokenValiditySeconds(60*60 );
        // refresh_token默认1天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 );
        return tokenServices;
    }

   /* @Bean
    public JwtAccessTokenConverter  jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("weiller");
        return  jwtAccessTokenConverter;
    }

    @Bean
    public JwtTokenStore  jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }*/
}
