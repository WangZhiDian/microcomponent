package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.InsertUserService;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateUserController {

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

}
