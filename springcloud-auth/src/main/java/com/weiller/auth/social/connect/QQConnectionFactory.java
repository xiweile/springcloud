package com.weiller.auth.social.connect;

import com.weiller.auth.social.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * qq连接服务提供商的工厂类
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ>{

    public QQConnectionFactory(String providerId,  String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret),  new QQAdapter());
    }
}
