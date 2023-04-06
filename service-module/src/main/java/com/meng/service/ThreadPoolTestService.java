package com.meng.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThreadPoolTestService {

    /**
     * async注解省略了自己创建实现了runnable或者callable方法的类，省略了自己提交
     */
    @Async(value = "serviceTaskExector")
    public void print(String info) throws InterruptedException {

        log.info("async thread info before sleep");
        Thread.sleep(3000);
        log.info("async thread info after sleep");

    }

}
