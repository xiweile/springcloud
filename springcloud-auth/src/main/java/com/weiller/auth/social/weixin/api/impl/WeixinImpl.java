package com.weiller.auth.social.weixin.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiller.auth.social.weixin.api.Weixin;
import com.weiller.auth.social.weixin.api.WeixinUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

    //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
    private static final String WEIXIN_URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";

    /**
     * appId 配置文件读取
     */
    private String appId;

    /**
     * 工具类
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造方法获取openId,此网站上唯一对应用户身份的标识
     */
    public WeixinImpl(String accessToken) {
        // access_token 作为查询参数携带
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WeixinUserInfo getUserInfo(String openId) {
        String url = String.format(WEIXIN_URL_GET_USER_INFO, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("Winxin认证 WEIXIN_URL_GET_USER_INFO={} result={}",WEIXIN_URL_GET_USER_INFO,result);

        WeixinUserInfo userInfo = null;
        if(!StringUtils.contains(result,"errcode")){
            try {
                userInfo = objectMapper.readValue(result,WeixinUserInfo.class);
            } catch (Exception e) {
                throw new RuntimeException( "Winxin用户身份获取失败");
            }
        }

        return userInfo;
    }

    /**
     * 使用utf-8 替换默认的ISO-8859-1编码
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }
}
