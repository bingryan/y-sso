package com.ryanbing.config.interceptor;

import com.ryanbing.annotation.Authorization;
import com.ryanbing.annotation.PublicApi;
import com.ryanbing.consts.Const;
import com.ryanbing.entity.ResponseCode;
import com.ryanbing.entity.ResponseResult;
import com.ryanbing.manager.TokenManager;
import com.ryanbing.utils.CookieUtil;
import com.ryanbing.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 权限拦截器
 *
 * @author ryan
 **/
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private TokenManager tokenManager;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean isPublic = (handlerMethod.hasMethodAnnotation(PublicApi.class)
                || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(PublicApi.class));
        if (isPublic) {
            return true;
        }
        String authorization = CookieUtil.getValue(request, Const.DOMAIN.AUTHORIZATION);

        // 是否登录
        boolean isNotVerified = (handlerMethod.hasMethodAnnotation(Authorization.class)
                || handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(Authorization.class))
                && !tokenManager.exists(authorization);
        if (isNotVerified) {
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter writer = response.getWriter()) {
                String json = JsonUtils.toJson(ResponseResult.error(ResponseCode.UNAUTHORIZED.getCode(), "用户未登入"));
                writer.write(json);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
