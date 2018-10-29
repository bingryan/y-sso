package com.ryanbing.utils;


import com.ryanbing.consts.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ryan
 **/
@Slf4j
public class CookieUtil {
    private static final String COOKIE_PATH = "/";

    private static final int COOKIE_EXTIME = 60 * 60 * 24 * 31 * 12;


    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }


    public static void setCookie(HttpServletResponse response, String key, String value, boolean isKeep) {
        // cookie 设置为-1 表示流出浏览器之后就删除
        int age = isKeep ? COOKIE_EXTIME : -1;
        setCookie(response, key, value, null, COOKIE_PATH, age, true);
    }

    public static void setCookie(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        log.info("Write cookiePath:{} cookieName:{},cookieValue:{}", cookie.getPath(), cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }


    public static String getAuthToekn(HttpServletRequest request) {
        Cookie cookie = getCookie(request, Const.DOMAIN.AUTHORIZATION);
        if (cookie == null) {
            return UniqueKeyGenerator.uniqueToken(Const.DOMAIN.AUTHORIZATION);
        }
        return cookie.getValue();
    }

    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }


    public static String setLoginXSRFTOKEN(String content, HttpServletResponse response, String isKeep) {

        String resToken = UniqueKeyGenerator.uniqueToken(content);
        String token = String.format(Const.DOMAIN.LOGIN_TOKEN_FORMAT, resToken);
        setCookie(response, Const.DOMAIN.XSRF_TOKEB, token, Const.TRUE.equals(isKeep));
        return resToken;
    }


    public static void remove(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), Const.DOMAIN.XSRF_TOKEB)) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }


}
