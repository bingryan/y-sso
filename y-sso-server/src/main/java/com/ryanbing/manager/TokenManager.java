package com.ryanbing.manager;

import java.util.concurrent.TimeUnit;

/**
 * token 管理
 *
 * @author ryan
 **/
public interface TokenManager {


    String create(Object content);

    void set(String key, Object value, long timeout, TimeUnit unit);


    String getString(String key);

    Object getObject(String key);


    public boolean exists(String token);

    public boolean delete(String token);
}
