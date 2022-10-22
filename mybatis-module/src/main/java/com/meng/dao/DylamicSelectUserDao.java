package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DylamicSelectUserDao {


    List<User> getUserByConditionIf(User user);

    List<User> getUserByConditionWhere(User user);

    List<User> getUserByConditionTrim(User user);

    List<User> getUserByConditionChoose(User user);

    List<User> getUserByForeachIds(List<Long> userIdList);

    List<User> getUserByForeachUsers(@Param("users") List<User> users);


}
