package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.disruptor.producer.MsgProducer;
import com.meng.disruptor.producer.MsgProducer2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 disruptor 操作设置
 */
@Slf4j
@RestController
public class DisruptorOpController {

    @Resource
    MsgProducer msgProducer;

    @Resource
    MsgProducer2 msgProducer2;

    @GetMapping(value = "/disruptor/send")
    public ApiResult<Object> deal(@RequestParam("info") String info) {

        msgProducer2.send(info);

        return ApiResult.success("success");
    }



}
