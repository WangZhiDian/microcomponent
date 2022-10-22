package com.meng.service;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeleteUserService {

    int deleteUserById(Long id);

    int batchDeleteByIds(@Param("userIds") List<Long> userIds);

    int batchDeleteByUsers(@Param("users") List<User> userList);

}
