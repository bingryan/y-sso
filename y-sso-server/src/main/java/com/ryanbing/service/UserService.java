package com.ryanbing.service;

import com.ryanbing.entity.ResponseResult;
import com.ryanbing.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-10-23
 */
public interface UserService extends IService<User> {

    ResponseResult<String> login(String username, String password, HttpServletRequest request, HttpServletResponse response,String isKeep);

    ResponseResult<String> logout(HttpServletRequest request, HttpServletResponse response);

    ResponseResult<User> getUserInfo(HttpServletRequest request);

    ResponseResult<User> updateUserInfo(HttpServletRequest request, HttpServletResponse response, User user);
}
