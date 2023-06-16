package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.bean.DataTransObj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
    springboot2.x以后，可以在启动类上面添加排除该生效
 @SpringBootApplication(exclude=SecurityAutoConfiguration.class)
 *
 */
@Slf4j
@RestController
public class AuthOpController {

    @GetMapping(value = "/auth/get")
    public ApiResult<Object> deal(@RequestParam("uid") String uid,
                                  @RequestParam("value") Long value) {


        return ApiResult.success("suc");
    }

    @PostMapping(value = "/login")
    public ApiResult<Object> login(@RequestParam("uname") String uname,
                                  @RequestParam("pwd") Long pwd) {


        return ApiResult.success("login suc");
    }

    @GetMapping(value = "/auth/post")
    public DataTransObj dealGetSave(@RequestBody DataTransObj dataTransObj) {
        return dataTransObj;
    }


}
