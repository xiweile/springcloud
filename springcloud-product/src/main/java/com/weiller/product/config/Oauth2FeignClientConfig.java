package com.weiller.product.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@EnableOAuth2Client
@Configuration
public class Oauth2FeignClientConfig {


  /*  @Bean
    public OAuth2ClientContext oauth2ClientContext() {

        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
    }
*/
    @Bean
    public RestClientErrorDecoder errorDecoder() {

        return new RestClientErrorDecoder();
    }

    @Bean
    protected RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("oauth2ClientContext") @Autowired OAuth2ClientContext oauth2ClientContext,
                                                               OAuth2ProtectedResourceDetails resourceDetails) {

        return new OAuth2FeignRequestInterceptor( oauth2ClientContext, resourceDetails);
    }

    @Bean
    @LoadBalanced
    public OAuth2RestTemplate oauth2RestTemplate(@Qualifier("oauth2ClientContext") @Autowired OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details, oauth2ClientContext);
        oAuth2RestTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        oAuth2RestTemplate.setAccessTokenProvider(provider);
        return oAuth2RestTemplate;
    }
}
