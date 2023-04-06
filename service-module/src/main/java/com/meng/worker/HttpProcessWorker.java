package com.meng.worker;

import com.meng.bean.ApiResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HttpProcessWorker implements Runnable{

    DeferredResult<Object> deferredResult;

    HttpServletResponse response;

    HttpServletRequest request;

    String type;

    public HttpProcessWorker(DeferredResult<Object> deferredResult,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             String type) {
        this.request = request;
        this.response = response;
        this.deferredResult = deferredResult;
        this.type = type;
    }

    @SneakyThrows
    @Override
    public void run() {
        String type = request.getParameter("thread-type");
        log.info("in thread, thread type:{}", type);
        if ("delay".equals(type)) {
            Thread.sleep(4500);
            deferredResult.setResult(ApiResult.success("thread delay return"));
        } else if ("normal".equals(type)) {
            ApiResult<Object> ret = ApiResult.success("thread normal return");
            deferredResult.setResult(ret);
        } else if ("delay-1".equals(type)) {
             Thread.sleep(1500);
            deferredResult.setResult(ApiResult.success("thread delay one second"));
        }
    }
}
