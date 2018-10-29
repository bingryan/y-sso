package com.ryanbing.consts;

/**
 * 常量
 *
 * @author ryan
 **/
public final class Const {
    public static final String USERNAME = "y.sso.username";
    public static final String DOMAIN_XSRF_TOKEB = "ysso.xsrf.token";
    public static final String TRUE = "true";
    public static final String FALSE = "false";


    public interface REDIS {
        /**
         * redis sig 的缓存时间 60000 毫秒
         */
        long SIG_TIMEOUT = 1000 * 60 * 30;
        /**
         * redis 用户信息的缓存时间 60000 毫秒
         */
        long USER_INFO_EXTIME = 60000 * 24 * 7;
    }

    public interface DOMAIN {
        String XSRF_TOKEB = "ysso.xsrf.token";
        String LOGIN_TOKEN_FORMAT = "%s";
        //String LOGIN_TOKEN_FORMAT = "%s|login";
        // 测试环境的token 和XSRF_TOKEB的一样
        String AUTHORIZATION = "ysso.xsrf.token";
        //String AUTHORIZATION = "au";
    }
}