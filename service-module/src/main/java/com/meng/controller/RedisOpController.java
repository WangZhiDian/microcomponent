package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.bean.DataTransObj;
import com.meng.redis.dao.IRedisInfoDao;
import com.meng.util.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用二次封装的redis的操作工具类，该redis util支持主备，集群，哨兵三种模式下的访问。
 */
@Slf4j
@RestController
public class RedisOpController {

    @Autowired
    IRedisInfoDao iRedisInfoDao;

    @GetMapping(value = "/redis/set")
    public ApiResult<Object> deal(@RequestParam("key") String key, @RequestParam("value") String value) {

        iRedisInfoDao.saveStrInfo(key, value);

        return ApiResult.success("suc");
    }

    @GetMapping(value = "/redis/get")
    public ApiResult<Object> deal(HttpServletRequest request,
                                  @RequestParam("key") String key) {
        String token = request.getHeader("token");
        log.info("redis get token:{}", token);
        String value = iRedisInfoDao.getStrInfo(key);
        //String value2 = SerializeUtil.
        //String key = "query.user.privilege_SUFFIX_SERVICE_SQL";
        //Object obj2 = SerializeUtil.unserialize(redis.get((key).getBytes()));
        //Object obj = SerializeUtil.unserialize(iRedisInfoDao.get((key).getBytes()));
        Object obj3 = SerializeUtil.unserialize(iRedisInfoDao.getBitMapInfo(key));



        return ApiResult.success(value);
    }

    @GetMapping(value = "/redis/post")
    public ApiResult<Object> dealGetSave(HttpServletRequest request, @RequestBody DataTransObj dataTransObj) {
        String token = request.getHeader("token");
        iRedisInfoDao.saveStrInfo(dataTransObj.getKey(), dataTransObj.getValue());
        return ApiResult.success(dataTransObj.toString());
    }

    @PostMapping(value = "/redis/post")
    public ApiResult<Object> dealPostPost(HttpServletRequest request,
                                          @RequestBody DataTransObj dataTransObj) {
        String token = request.getHeader("token");
        log.info("token in post:{}", token);
        iRedisInfoDao.saveStrInfo(dataTransObj.getKey(), dataTransObj.getValue());
        return ApiResult.success(dataTransObj.toString());
    }

}
