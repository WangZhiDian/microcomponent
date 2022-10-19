package com.meng.controller;

import com.alibaba.fastjson.JSONObject;
import com.meng.domain.User;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteUserController {

    @Autowired
    UserService userService;



    @DeleteMapping(value = "/delete/deleteUserByID")
    public String deleteUserById(@Param("id") Long id) {
        userService.deleteUserById(id);

        return "success";
    }
}
