package com.weiller.product.config;

import feign.RequestInterceptor;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class Oauth2FeignClientConfig {


    @Bean
    public OAuth2ClientContext oauth2ClientContext() {

        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
    }

    @Bean
    public RestClientErrorDecoder errorDecoder() {

        return new RestClientErrorDecoder();
    }

    @Bean
    protected RequestInterceptor oauth2FeignRequestInterceptor(  OAuth2ClientContext oauth2ClientContext,
             OAuth2ProtectedResourceDetails resourceDetails) {

        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, resourceDetails);
    }


    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details, oauth2ClientContext);
        oAuth2RestTemplate.setMessageConverters(messageConverters);
        return oAuth2RestTemplate;
    }


}
