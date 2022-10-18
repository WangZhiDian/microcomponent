package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/getUserByID")
    public String getUserById(@Param("id") Long id) {

        User user = userService.getUserById(id);
        String ret = JSONObject.toJSONString(user);
        return ret;
    }

    @PostMapping(value = "/createUser")
    public String create(@RequestBody User user) {
        userService.insertUser(user);
        return "success";
    }

    @PutMapping(value = "/updateUser")
    public String update(@RequestBody User user) {

        userService.updateUser(user);
        return "success";
    }

    @DeleteMapping(value = "/deleteUserByID")
    public String deleteUserById(@Param("id") Long id) {
        userService.deleteUserById(id);

        return "success";
    }
}
