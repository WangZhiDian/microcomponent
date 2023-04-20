package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.dao.IKafkaProducerInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**

 */
@Slf4j
@RestController
public class KafkaPushController {

    @Resource(name = "KafkaProducerInfoDao")
    IKafkaProducerInfoDao iKafkaProducerInfoDao;


    @GetMapping(value = "/kafka/product")
    public ApiResult deal(@RequestParam("topic") String topic,
                          @RequestParam("key") String key,
                          @RequestParam("value") String value) throws ExecutionException, InterruptedException, TimeoutException {

        iKafkaProducerInfoDao.sendSyncMessageInfo(topic, key, value, 3000);
        return ApiResult.success("success");
    }



}
