package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateUserController {

    @Autowired
    UserService userService;


    @PostMapping(value = "/create/createUser")
    public String create(@RequestBody User user) {
        userService.insertUser(user);
        return "success";
    }

}
