package com.weiller.auth.social.weixin.config;

import com.weiller.auth.social.SocialConnectView;
import com.weiller.auth.social.weixin.connect.WeixinConnectionFactory;
import com.weiller.auth.utils.SecurityConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 社交配置主类
 */
// 当配置了app-id的时候才启用
//@ConditionalOnProperty(prefix = "constants.security.social.weixin", name = "app-id")
@Configuration
@Order(2)
public class WeixinAuthConfig extends SocialAutoConfigurerAdapter {
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeixinConnectionFactory(SecurityConstants.DEFAULT_SOCIAL_WEIXIN_PROVIDER_ID,
                SecurityConstants.DEFAULT_SOCIAL_WEIXIN_APP_ID,SecurityConstants.DEFAULT_SOCIAL_WEIXIN_APP_SECRET);
    }

    /**
     * /connect/weixin POST请求,绑定微信返回connect/weixinConnected视图
     * /connect/weixin DELETE请求,解绑返回connect/weixinConnect视图
     * @return
     */
    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new SocialConnectView();
    }
}
