package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.redis.dao.IRedisInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用二次封装的redis的操作工具类，该redis util支持主备，集群，哨兵三种模式下的访问。
 */
@Slf4j
@RestController
public class RedisOpController {

    @Autowired
    IRedisInfoDao iRedisInfoDao;

    @GetMapping(value = "/redis/set")
    public ApiResult<Object> deal(@RequestParam("key") String key, @RequestParam("value") String value) throws InterruptedException {

        iRedisInfoDao.saveStrInfo(key, value);

        return ApiResult.success("suc");
    }

    @GetMapping(value = "/redis/get")
    public ApiResult<Object> deal(@RequestParam("key") String key) throws InterruptedException {

        String value = iRedisInfoDao.getStrInfo(key);

        return ApiResult.success(value);
    }

}
