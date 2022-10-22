package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InsertUserDao {


    User getUserById(@Param("id") Long id);

    int insertUserWithPrimaryKey(User user);

    int insertUserWithGenPrimaryKey(User user);

    int replaceIntoUserValues(@Param("user") User user);

    int replaceIntoUserSet(User user);

    int batchReplaceIntoUser(@Param("userList") List<User> userList);


    int insertUserOnDuplicate(@Param("user") User user);

    int batchInsertUserOneSql(@Param("userList") List<User> userList);

    int batchInsertUserMultiSql(@Param("userList") List<User> userList, @Param("age") int age);


}
