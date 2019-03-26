package com.weiller.auth.social.weixin.connect;

import com.weiller.auth.social.weixin.api.Weixin;
import com.weiller.auth.social.weixin.api.impl.WeixinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * QQ连接服务提供商
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

    /**
     * 微信获取授权码的url
     */
    private static final String WEIXIN_URL_AUTHORIZE  = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url(微信在获取accessToken时也已经返回openId)
     */
    private static final String WEIXIN_URL_ACCESS_TOKEN  = "https://api.weixin.qq.com/sns/oauth2/access_token";

    private String appId;

    public WeixinServiceProvider(String appId, String appSecret) {
        super( new WeixinOAuth2Template(appId,appSecret,WEIXIN_URL_AUTHORIZE,WEIXIN_URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public Weixin getApi(String accessToken) {
        return new WeixinImpl(accessToken);
    }
}
