package com.meng.worker;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolTestWorker implements Runnable{

    private String info;

    public ThreadPoolTestWorker(String info) {
        this.info = info;
    }

    @SneakyThrows
    @Override
    public void run() {

        log.info("direct thread before sleep");
        Thread.sleep(2000);
        log.info("direct thread after sleep");

    }
}
