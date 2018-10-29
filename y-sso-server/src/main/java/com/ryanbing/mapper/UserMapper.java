package com.ryanbing.mapper;

import com.ryanbing.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ryan
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByPassword(@Param("username") String username, @Param("password") String password);

    User selectByUsername(@Param("username") String username);
}
