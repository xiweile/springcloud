package com.weiller.auth.social.config;

import com.weiller.auth.social.SocialConnectView;
import com.weiller.auth.social.connect.QQConnectionFactory;
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
//@ConditionalOnProperty(prefix = "constants.security.social.qq", name = "app-id")
@Configuration
@Order(2)
public class QQAuthConfig extends SocialAutoConfigurerAdapter {
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(SecurityConstants.DEFAULT_SOCIAL_QQ_PROVIDER_ID,
                SecurityConstants.DEFAULT_SOCIAL_QQ_APP_ID,SecurityConstants.DEFAULT_SOCIAL_QQ_APP_SECRET);
    }

    /**
     * /connect/qq POST请求,绑定微信返回connect/weixinConnected视图
     * /connect/qq DELETE请求,解绑返回connect/weixinConnect视图
     * @return
     */
    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new SocialConnectView();
    }
}
