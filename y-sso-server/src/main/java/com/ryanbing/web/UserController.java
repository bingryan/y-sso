package com.ryanbing.web;

import com.ryanbing.annotation.Authorization;
import com.ryanbing.entity.ResponseResult;
import com.ryanbing.exception.YSsoException;
import com.ryanbing.kaptcha.spring.boot.RedisKaptcha;
import com.ryanbing.pojo.User;
import com.ryanbing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @author ryan
 **/
@Validated
@RestController
@RequestMapping(value = "/ysso/api/1")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisKaptcha redisKaptcha;



    @PostMapping(value = "/user")
    public ResponseResult<String> login(@NotBlank(message = "用户不能空") String username,
                                        @NotBlank(message = "密码不能空") String password,
                                        @NotBlank(message = "请输入验证码") String captcha,
                                        @RequestParam(value = "isKeep", required = false) String isKeep,
                                        HttpServletRequest request, HttpServletResponse response) {
        if (!redisKaptcha.validCaptcha(request, captcha)) {
            return ResponseResult.error("请确认验证码");
        }
        return userService.login(username, password, request, response, isKeep);
    }


    @Authorization
    @GetMapping(value = "/user")
    public ResponseResult<User> getUserInfo(HttpServletRequest request) {
        return userService.getUserInfo(request);
    }


    @Authorization
    @DeleteMapping(value = "/user")
    public ResponseResult<String> logout(HttpServletRequest request, HttpServletResponse response) {


        return userService.logout(request, response);
    }

    @Authorization
    @PutMapping(value = "/user")
    public ResponseResult<User> updateUserInfo(HttpServletRequest request, HttpServletResponse response, @Valid User user) {
        return userService.updateUserInfo(request, response, user);
    }


    @RequestMapping(value = "/captcha", method = {RequestMethod.POST, RequestMethod.GET})
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            redisKaptcha.setCaptcha(request, response);
        } catch (IOException e) {
            throw new YSsoException("请求验证码错误");
        }
    }


}
