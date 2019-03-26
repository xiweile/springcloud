package com.weiller.auth.social.weixin.api;

/**
 * 微信认证接口
 */
public interface Weixin {

    /**
     * 获取用户信息
     *
     * @return
     */
    WeixinUserInfo getUserInfo(String openId);
}
