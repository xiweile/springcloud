package com.weiller.identity.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import com.weiller.utils.FilterPathConfigProperties;
import com.weiller.utils.IdentitySessionClient;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * 登出请求后过滤器
 *
 * @author weiller
 * @version 1.0,2018年10月16日13:01:45
 */
@Component
@Slf4j
public class AfterLogoutFilter extends ZuulFilter {
    @Autowired
    private FilterPathConfigProperties filterPathConfig;

    @Autowired
    private IdentitySessionClient identitySessionClient;

    @Override
    public String filterType() {
        return "post"; //在routing和error过滤器之后被调用
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getRequestURI().equals(filterPathConfig.getLogout())) {
            return true;
        }else  return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            Object zuulResponse = ctx.get("zuulResponse");
            if (zuulResponse != null) {
                RibbonHttpResponse ribbonHttpResponse = (RibbonHttpResponse) zuulResponse;

                int statusCode = ribbonHttpResponse.getRawStatusCode();
                if (statusCode == 200) {
                    InputStream body = ribbonHttpResponse.getBody();
                    JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(body));

                    String sessionid = null;
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            String name = cookie.getName();
                            if (IdentitySessionClient.SESSION_ID_KEY.equalsIgnoreCase(name)) {// 获取sessionid值
                                sessionid = cookie.getValue();
                            }
                        }
                    }
                    if (sessionid != null) {
                        identitySessionClient.removeSession(sessionid);
                        ctx.setResponseBody(jsonObject.toJSONString());
                    } else {
                        Msg msg = new Msg();
                        msg.setCode(MsgCode.ERROR.getCode());
                        msg.setMsg("登出异常");
                        ctx.setResponseBody(JSON.toJSONString(msg));
                    }
                }
            }
            ctx.setResponseStatusCode(200);
        } catch (Exception e) {
            ctx.setResponseStatusCode(500);
            log.error("登出时删除session 异常 ", e);
        }
        ctx.setSendZuulResponse(true);
        return null;
    }
}
