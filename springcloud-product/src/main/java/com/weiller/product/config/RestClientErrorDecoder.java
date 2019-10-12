package com.weiller.product.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

import static feign.FeignException.errorStatus;

public class RestClientErrorDecoder implements ErrorDecoder {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Override
    public Exception decode(String methodKey, Response response) {

        if (HttpStatus.UNAUTHORIZED.value() == response.status()) {

            oauth2ClientContext.setAccessToken(null);
        }

        return errorStatus(methodKey, response);
    }
}