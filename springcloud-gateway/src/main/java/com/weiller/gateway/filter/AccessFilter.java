package com.weiller.gateway.filter;


import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.weiller.api.comm.IdentitySessionClient;
import com.weiller.gateway.config.FilterPathConfig;
import com.weiller.utils.comm.UUIDGenerator;
import com.weiller.utils.model.Msg;
import com.weiller.utils.model.MsgCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 访问入口过滤器
 *
 * @author weiller
 * @version 1.0,2018-10-11 15:12:45
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter{

    @Autowired
    private FilterPathConfig filterPathConfig;

    @Autowired
    private IdentitySessionClient identitySessionClient;

    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        log.info(" accessFilter income url : {}",request.getRequestURL());

        String sessionid = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie: cookies){
                String name = cookie.getName();
                if(IdentitySessionClient.SESSION_ID_KEY.equalsIgnoreCase(name)){// 获取sessionid值
                    sessionid = cookie.getValue();
                }
            }
        }

        if(StringUtils.isEmpty(sessionid)){
            // 首次访问
            sessionid = UUIDGenerator.getUUID2();
        }

        // 设置cookie
        Cookie cookie = new Cookie(IdentitySessionClient.SESSION_ID_KEY, sessionid);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 网关路由请求头设置
        ctx.addZuulRequestHeader("Cookie",IdentitySessionClient.SESSION_ID_KEY+"="+sessionid);

        // 检查是否为忽略身份认证
        boolean isIngore = false;// 默认过滤类型为 不忽略

        String ignores = filterPathConfig.getIgnores();
        String[] ignoreArray= ignores.split(",");
        for (int i = 0; i < ignoreArray.length; i++) {
            if (request.getRequestURI().contains(ignoreArray[i])) {
                isIngore = true;
                break;
            }
        }

        if(!isIngore) {
            // 身份认证
            HttpSession session = request.getSession();
            Object sessionInfo = identitySessionClient.getSessionInfo(sessionid);
            if (sessionInfo != null) {
                // 已登录
                ctx.setSendZuulResponse(true); // 允许路由
                ctx.setResponseStatusCode(200);
            } else {
                // 未登录 或已过期
                ctx.setSendZuulResponse(false);// 不进行路由
                //ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                ctx.setResponseStatusCode(200);
                Msg msg = new Msg();
                msg.setCode(MsgCode.AUTH_INVALID.getCode());
                msg.setMsg(MsgCode.AUTH_INVALID.getDesc());

                ctx.setResponseBody( JSONObject.toJSONString(msg));
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            }
        }
        return null;
    }
}
