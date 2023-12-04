package com.meng.service;

//import javafx.concurrent.Worker;
//import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
//import sun.jvm.hotspot.runtime.ObjectSynchronizer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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


    public void aaa() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Thread t = new Thread();
        executor.submit(runnable);
        executor.submit(t);
        executor.execute(t);


        synchronized (new Object()) {

        }
    }

}
