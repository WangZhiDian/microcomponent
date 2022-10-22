package com.meng.service;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsertUserService {


    int insertUserWithPrimaryKey(User user);

    int insertUserWithGenPrimaryKey(User user);

    /**
     * replace是insert的增强版
     * replace into 首先尝试插入数据到表中，
     * 1. 如果发现表中已经有此行数据（根据主键或者唯一索引判断）则先删除此行数据，然后插入新的数据。
     * 2. 否则，直接插入新数据
     * 注意：如果有自增主键，并且唯一件有重复，然后多次执行replace后，可能引起主键值过长，不是连续自增
     *      如果操作的表和其他表有主键关联关系，该方法可能会因为记录被删除而丢失关联关系
     */
    int replaceIntoUserValues(User user);

    int replaceIntoUserSet(User user);

    int batchReplaceIntoUser(@Param("userList") List<User> userList);


    /**
     * 该方法有一定死锁的概率
     * https://blog.csdn.net/li563868273/article/details/105213266?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105213266-blog-104250372.pc_relevant_multi_platform_whitelistv3&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105213266-blog-104250372.pc_relevant_multi_platform_whitelistv3&utm_relevant_index=1
     *
     */
    int insertUserOnDuplicate(User user);

    int batchInsertUserOneSql(@Param("userList") List<User> userList);

    int batchInsertUserMultiSql(@Param("userList") List<User> userList, @Param("age") int age);


}
