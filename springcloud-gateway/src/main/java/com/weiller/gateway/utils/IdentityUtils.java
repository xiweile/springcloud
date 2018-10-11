package com.weiller.gateway.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 身份工具类
 */
public class IdentityUtils {

    public static final String TOKEN_KEY = "token";

    public static final String SESSION_ID_KEY = "sessonid";


    public static Object getSessionInfo(HttpSession session, String sessionid) {
        return session.getAttribute(sessionid);
    }
}
