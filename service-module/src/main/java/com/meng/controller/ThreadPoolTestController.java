package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.service.ThreadPoolTestService;
import com.meng.worker.HttpProcessWorker;
import com.meng.worker.ThreadPoolTestWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述总结：
 */
@Slf4j
@RestController
public class ThreadPoolTestController {

    private static long http_timeout = 3;// 超时30秒


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ThreadPoolTestService threadPoolTestService;

    /**
     * 说用：
     */
    @GetMapping(value = "/thread/{info}")
    public ApiResult<Object> deal(@PathVariable("info") String info) throws InterruptedException {

        if ("async".equals(info)) {
            threadPoolTestService.print(info);
        }
        if ("direct".equals(info)) {
            threadPoolTaskExecutor.execute(new ThreadPoolTestWorker(info));
        }

        return ApiResult.success("suc");
    }



}
