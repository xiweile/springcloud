package com.weiller.auth.social.weixin.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

@Data
public class WeixinAccessGrant extends AccessGrant {

    private String openId;

    public WeixinAccessGrant() {
        super("");
    }

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }
}