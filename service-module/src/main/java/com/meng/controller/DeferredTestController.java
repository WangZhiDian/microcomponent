package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.worker.HttpProcessWorker;
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
API接口需要在指定时间内将异步操作的结果同步返回给前端时；
Controller处理耗时任务，并且需要耗时任务的返回结果时；
当一个请求到达API接口，如果该API接口的return返回值是DeferredResult，在没有超时或者DeferredResult对象设置setResult时，接口不会返回，但是Servlet容器线程会结束，DeferredResult另起线程来进行结果处理(即这种操作提升了服务短时间的吞吐能力)，并setResult，如此以来这个请求不会占用服务连接池太久，如果超时或设置setResult，接口会立即返回。
使用DeferredResult的流程：
浏览器发起异步请求
请求到达服务端被挂起
向浏览器进行响应，分为两种情况：
3.1 调用DeferredResult.setResult()，请求被唤醒，返回结果
3.2 超时，返回一个你设定的结果
浏览得到响应，再次重复1，处理此次响应结果

 * 描述总结：DeferredResult解决的问题为短时间内的吞吐量问题，请求中有耗时任务或者异步任务，
 *         通过该方式，http现将请求资源让出来相应网上的其他请求，避免同步处理时，http的连接数资源不足问题
 */
@Slf4j
@RestController
public class DeferredTestController {

    private static long http_timeout = 3;// 超时30秒


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 说用：在DeferredResult中设置的超时时间，如果异步处理的时长超过该设置时间，则会直接异常返回
     * 其中的超时时间包括但不限于同步逻辑中设置setResult的时长，或者异步通过线程处理设置的setResult时长
     * 即处理改次请求的总时长
     */
    @GetMapping(value = "/defered/{info}")
    public DeferredResult<Object> deal(@PathVariable("info") String info,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws InterruptedException {

        DeferredResult<Object> result = new DeferredResult<>(http_timeout * 1000);

        if ("delay".equals(info)) {
            Thread.sleep(4000);
        } else if ("normal".equals(info)) {
            ApiResult<Object> ret = ApiResult.success("normal return");
            result.setResult(ret);
        } else if ("thread".equals(info)) {
            threadPoolTaskExecutor.execute(new HttpProcessWorker(result, request, response, info));
            log.info("in main thread info:{}", info);
        } else {
            result.setErrorResult("nothing");
        }

        return result;
    }



}
