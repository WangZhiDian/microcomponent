package com.meng.controller;


import com.meng.domain.User;
import com.meng.service.DeleteUserService;
import com.meng.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeleteUserController {

    @Autowired
    DeleteUserService deleteUserService;



    @DeleteMapping(value = "/delete/deleteUserById")
    public String deleteUserById(@Param("id") Long id) {
        int ret = deleteUserService.deleteUserById(id);

        return "delete success,delete:" + ret + " records";
    }

    @DeleteMapping(value = "/delete/batchDeleteByIds")
    public String batchDeleteByIds(@RequestBody List<Long> userIdList) {

        int ret = deleteUserService.batchDeleteByIds(userIdList);

        return "delete success,delete:" + ret + " records";
    }

    @DeleteMapping(value = "/delete/batchDeleteByUsers")
    public String batchDeleteByUsers(@RequestBody List<User> userList) {

        int ret = deleteUserService.batchDeleteByUsers(userList);

        return "delete success,delete:" + ret + " records";
    }

}
