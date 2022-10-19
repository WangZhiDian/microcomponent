package com.meng.dao;

import com.meng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SelectUserDao {


    User getUserById(Long id);

    User getUserByNameAndAge(String name, int age);

    User getUserByNameAndAge2(@Param("name") String name, @Param("age") int age);

    User getUserByUser(User user);

    User getUserBySelective(User user);

    List<User> getUsersByName(User user);

    List<User> getUsersByMapInfo(Map map);

    int countByName(@Param("name") String name);

    Map<String, String> getUserToMap(@Param("id") Long id);

    List<Map<String, String>> getAllUserToMap();


}
