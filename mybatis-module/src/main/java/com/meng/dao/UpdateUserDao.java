package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UpdateUserDao {

    int updateUser(User user);

    int batchUpdateUser(@Param("userList") List<User> userList, @Param("age") int age);

    int batchUpdateUserMultiSql(@Param("userList") List<User> userList);

    int updateByCondition(@Param("id") Long id, @Param("name") String name);
}
