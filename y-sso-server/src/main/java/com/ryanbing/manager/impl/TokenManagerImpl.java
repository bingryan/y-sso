package com.ryanbing.manager.impl;

import com.ryanbing.exception.YSsoException;
import com.ryanbing.manager.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ryan
 **/

@Component
public class TokenManagerImpl implements TokenManager {

    private static Logger logger = LoggerFactory.getLogger(TokenManagerImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public String create(Object content) {
        String token = null;
        try {
            token = UUID.randomUUID().toString().replace("-", "");
            redisTemplate.opsForValue().set(token, content);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }
        return token;
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
            stringObjectValueOperations.set(key, value, timeout, unit);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }
    }


    @Override
    public String getString(String key) {
        String res = null;
        try {
            ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
            res = (String) stringObjectValueOperations.get(key);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }
        return res;
    }

    @Override
    public Object getObject(String key) {
        Object res = null;
        try {
            ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
            res = stringObjectValueOperations.get(key);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }
        return res;
    }

    @Override
    public boolean exists(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        boolean res = false;
        try {
            res = redisTemplate.hasKey(value);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }

        return res;
    }

    @Override
    public boolean delete(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        boolean res = false;
        try {
            res = redisTemplate.delete(value);
        } catch (Exception e) {
            logger.info("{}", e);
            throw new YSsoException("Service Unavailable");
        }
        return res;


    }
}
