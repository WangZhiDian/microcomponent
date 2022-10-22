package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeleteUserDao {


    User getUserById(@Param("id") Long id);

    int deleteUserById(Long id);

    int batchDeleteByIds(@Param("userIds") List<Long> userIds);

    int batchDeleteByUsers(@Param("users") List<User> userList);

}
