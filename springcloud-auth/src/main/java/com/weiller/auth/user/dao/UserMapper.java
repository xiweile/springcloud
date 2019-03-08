package com.weiller.auth.user.dao;

import com.weiller.auth.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findAll();
    User  getByUsernameAndPassword(@Param("username") String username,
                                   @Param("password") String password);

    User getByUsername(@Param("username") String username);

}