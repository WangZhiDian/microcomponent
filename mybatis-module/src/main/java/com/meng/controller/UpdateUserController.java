package com.meng.controller;

import com.meng.domain.User;
import com.meng.service.UpdateUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UpdateUserController {

    @Resource
    private UpdateUserService updateUserService;

    @PutMapping(value = "/update/updateUser")
    public String update(@RequestBody User user) {

        int ret = updateUserService.updateUser(user);
        return "update success, " + ret + "  record";
    }

    @PutMapping(value = "/update/batchUpdateUser")
    public String batchUpdateUser(@RequestParam("age")int age,
                                  @RequestBody List<User> userList) {

        int ret = updateUserService.batchUpdateUser(userList, age);
        return "update success, " + ret + "  record";
    }

    @PutMapping(value = "/update/batchUpdateUserMultiSql")
    public String batchUpdateUserMultiSql(@RequestBody List<User> userList) {

        int ret = updateUserService.batchUpdateUserMultiSql(userList);
        return "update success, " + ret + "  record";
    }

    @PutMapping(value = "/update/updateByCondition")
    public String updateByCondition(@RequestParam("uid") Long uid, @RequestParam("name") String name) {

        int ret = updateUserService.updateByCondition(uid, name);
        return "update success, " + ret + "  record";
    }





}
