package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.InsertUserService;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsertUserController {

    @Autowired
    InsertUserService insertUserService;


    @PostMapping(value = "/create/insertUserWithPrimaryKey")
    public String insertUserWithPrimaryKey(@RequestBody User user) {
        insertUserService.insertUserWithPrimaryKey(user);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @PostMapping(value = "/create/insertUserWithGenPrimaryKey")
    public String insertUserWithGenPrimaryKey(@RequestBody User user) {
        insertUserService.insertUserWithGenPrimaryKey(user);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @PostMapping(value = "/create/replaceIntoUserValues")
    public String replaceIntoUserValues(@RequestBody User user) {
        int ret = insertUserService.replaceIntoUserValues(user);
        return ret + "";
    }

    @PostMapping(value = "/create/batchReplaceIntoUser")
    public String batchReplaceIntoUser(@RequestBody List<User> userList) {
        int ret = insertUserService.batchReplaceIntoUser(userList);
        return ret + "";
    }

    @PostMapping(value = "/create/insertUserOnDuplicate")
    public String insertUserOnDuplicate(@RequestBody User user) {
        int ret = insertUserService.insertUserOnDuplicate(user);
        return ret + "";
    }

    @PostMapping(value = "/create/batchInsertUserOneSql")
    public String batchInsertUserOneSql(@RequestBody List<User> userList) {
        int ret = insertUserService.batchInsertUserOneSql(userList);
        return ret + "";
    }

    @PostMapping(value = "/create/batchInsertUserMultiSql")
    public String batchInsertUserMultiSql(@RequestBody List<User> userList,
                                          @RequestParam("age") int age) {
        int ret = insertUserService.batchInsertUserMultiSql(userList, age);
        return ret + "";
    }

}
