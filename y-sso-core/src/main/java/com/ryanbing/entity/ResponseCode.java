package com.ryanbing.entity;

/**
 * 返回码
 *
 * @author ryan
 **/
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(20000, "OK"),
    /**
     * 错误请求
     */
    ERROR(40000, "Bad Request"),

    /**
     * 用户未认证,前端接收到之后，返回登录页面
     */
    UNAUTHORIZED(40100, "Unauthorized"),
    /**
     * 请求次数过多
     */
    TOOMANYREQUESTS(42900, "Too Many Requests"),

    /**
     * 请求被禁止
     */
    FORBIDDEN(40300, "Forbidden"),


    /**
     * 服务维护中
     */
    SERVICEUNAVAILABLE(50300, "Service Unavailable");


    private final int code;
    private final String desc;


    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

