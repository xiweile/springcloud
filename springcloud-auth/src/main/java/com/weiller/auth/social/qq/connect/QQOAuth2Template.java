package com.weiller.auth.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 处理qq返回的令牌信息
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("QQ认证 获取accessToken的响应：responseStr={}" + responseStr);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        //http://wiki.connect.qq.com/使用authorization_code获取access_token
        //access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
        if(items.length>0){
            String accessToken  = StringUtils.substringAfterLast(items[0], "=");
            Long expiresIn  = new Long(StringUtils.substringAfterLast(items[1], "="));
            String refreshToken  = StringUtils.substringAfterLast(items[2], "=");
            return new AccessGrant(accessToken,null,refreshToken,expiresIn);
        }else{
            return null;
        }
    }

    /**
     * 处理qq返回的text/html 类型数据
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
