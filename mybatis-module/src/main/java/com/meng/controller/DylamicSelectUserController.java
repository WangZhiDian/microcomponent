package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.DynamicSelectUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DylamicSelectUserController {

    @Resource
    DynamicSelectUserService dynamicSelectUserService;


    @GetMapping(value = "/dynamic/select/getUserByConditionIf")
    public String getUserById(@Param("id") Long id) {
        User userParam = new User(id, null);
        List<User> userList = dynamicSelectUserService.getUserByConditionIf(userParam);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/dynamic/select/getUserByConditionWhere")
    public String getUserByConditionWhere(@Param("id") Long id) {
        User userParam = new User(id, null);
        List<User> userList = dynamicSelectUserService.getUserByConditionWhere(userParam);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/dynamic/select/getUserByConditionTrim")
    public String getUserByConditionTrim(@Param("id") Long id) {
        User userParam = new User(id, null);
        List<User> userList = dynamicSelectUserService.getUserByConditionTrim(userParam);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/dynamic/select/getUserByConditionChoose")
    public String getUserByConditionChoose(@Param("id") Long id) {
        User userParam = new User(id, null);
        List<User> userList = dynamicSelectUserService.getUserByConditionChoose(userParam);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/dynamic/select/getUserByForeachIds")
    public String getUserByForeachIds(@Param("id1") Long id1, @Param("id2") Long id2) {
        List<Long> ids = Arrays.asList(id1, id2);
        List<User> userList = dynamicSelectUserService.getUserByForeachIds(ids);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }

    @GetMapping(value = "/dynamic/select/getUserByForeachUsers")
    public String getUserByForeachUsers(@Param("id1") Long id1, @Param("id2") Long id2) {
        List<User> userListParam = new ArrayList<>();
        userListParam.add(new User(id1, null));
        userListParam.add(new User(id2, null));
        List<User> userList = dynamicSelectUserService.getUserByForeachUsers(userListParam);
        String ret = JSONObject.toJSONString(userList);
        return ret;
    }



}
