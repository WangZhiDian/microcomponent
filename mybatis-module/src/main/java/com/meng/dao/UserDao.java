package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public User getUserById(@Param("id") Long id);

    int insertUser(User user);

    int updateUser(User user);

    int deleteUserById(Long id);

}
