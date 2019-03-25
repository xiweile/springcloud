package com.weiller.auth.social.connect;

import com.weiller.auth.social.api.QQ;
import com.weiller.auth.social.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 适配spring Social默认的返回信息
 */
public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo userInfo = qq.getUserInfo();
        connectionValues.setProviderUserId(userInfo.getOpenId());
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
