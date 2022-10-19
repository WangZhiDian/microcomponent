package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateUserController {

    @Autowired
    UserService userService;



    @PutMapping(value = "/update/updateUser")
    public String update(@RequestBody User user) {

        userService.updateUser(user);
        return "success";
    }


}
