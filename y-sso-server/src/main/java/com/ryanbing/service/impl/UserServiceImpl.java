package com.ryanbing.service.impl;

import com.ryanbing.consts.Const;
import com.ryanbing.entity.ResponseCode;
import com.ryanbing.entity.ResponseResult;
import com.ryanbing.exception.YSsoException;
import com.ryanbing.manager.TokenManager;
import com.ryanbing.pojo.User;
import com.ryanbing.mapper.UserMapper;
import com.ryanbing.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryanbing.utils.CookieUtil;
import com.ryanbing.utils.UniqueKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryan
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenManager tokenManager;

    @Override
    public ResponseResult<String> login(String username, String password, HttpServletRequest request, HttpServletResponse response,String isKeep) {
        String passwd = UniqueKeyGenerator.md5Encrypt(password);

        User user = userMapper.selectByPassword(username, passwd);
        if (user == null) {
            return ResponseResult.error("请确认用户名和密码");
        }

        // 当用户登录的时候，信息是存放在redis 中的
        String token = CookieUtil.setLoginXSRFTOKEN(user.getUsername(), response, isKeep);


        tokenManager.set(token, user.getUsername(), Const.REDIS.USER_INFO_EXTIME, TimeUnit.MILLISECONDS);
        return ResponseResult.success("登陆成功");
    }

    @Override
    public ResponseResult<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String authToken = CookieUtil.getValue(request, Const.DOMAIN.AUTHORIZATION);
        if (authToken != null) {
            tokenManager.delete(authToken);
        }
        CookieUtil.remove(request, response);
        return ResponseResult.success(ResponseCode.UNAUTHORIZED.getCode(),"退出成功");
    }

    @Override
    public ResponseResult<User> getUserInfo(HttpServletRequest request) {
        String authToekn = CookieUtil.getAuthToekn(request);
        String username = tokenManager.getString(authToekn);

        if (StringUtils.isBlank(username)) {
            return ResponseResult.error(ResponseCode.UNAUTHORIZED.getCode(), "用户未登入");
        }



        User resUser = userMapper.selectByUsername(username);
        resUser.setPassword(StringUtils.EMPTY);
        return ResponseResult.success(resUser);
    }

    @Override
    public ResponseResult<User> updateUserInfo(HttpServletRequest request, HttpServletResponse response, User user) {
        String authToekn = CookieUtil.getAuthToekn(request);
        String username = tokenManager.getString(authToekn);

        if (StringUtils.isBlank(username)) {
            return ResponseResult.error(ResponseCode.UNAUTHORIZED.getCode(), "用户未登入");
        }

        User resUser = userMapper.selectByUsername(username);

        resUser.setPassword(UniqueKeyGenerator.md5Encrypt(user.getPassword()));

        try {
            userMapper.updateById(resUser);
        } catch (Exception e) {
            throw new YSsoException("更新密码失败");
        }

        // 强制登录
        CookieUtil.remove(request, response);
        return ResponseResult.success(ResponseCode.UNAUTHORIZED.getCode(), "密码更新成功！");
    }
}
