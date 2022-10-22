package com.meng.service;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpdateUserService {

    int updateUser(User user);

    int batchUpdateUser(@Param("userList") List<User> userList, @Param("age") int age);

    /**
     * 这种方式最简单，就是用foreach组装成多条update语句，但Mybatis映射文件中的sql语句默认是不支持以" ; " 结尾的，
     * 也就是不支持多条sql语句的执行。所以需要在连接mysql的url上加 &allowMultiQueries=true 这个才可以执行。
     */
    int batchUpdateUserMultiSql(@Param("userList") List<User> userList);

    int updateByCondition(@Param("id") Long id, @Param("name") String name);


}
