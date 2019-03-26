package com.weiller.auth.social.weixin.connect;

import com.weiller.auth.social.weixin.api.Weixin;
import com.weiller.auth.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 适配spring Social默认的返回信息
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinAdapter() {
    }

    public WeixinAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin winxin) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin  weixin, ConnectionValues connectionValues) {
        WeixinUserInfo userInfo = weixin.getUserInfo(openId);
        connectionValues.setProviderUserId(userInfo.getOpenid());
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getHeadimgurl());
        connectionValues.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(Weixin weixin) {
        return null;
    }

    @Override
    public void updateStatus(Weixin weixin, String s) {

    }
}
