package com.meng.controller;

import com.meng.bean.ApiResult;
import com.meng.bean.DataTransObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
    验证filter的添加和执行顺序
 *  1 运行顺序：filter 全局，interceptor 全局，aop 切面
 *  2 逻辑：filter before - 任务 - filter after
 *  3 filter 中未执行dofilter方法，在流程中会执行到异常退出逻辑
 */
@Slf4j
@RestController
public class FilterOpController {

    @GetMapping(value = "/filter/get")
    public ApiResult<Object> deal() {

        System.out.println("完成业务");
        return ApiResult.success("suc");
    }

    @GetMapping(value = "/filter/{info}")
    public ApiResult<Object> deal2(@PathVariable("info") String info) {

        System.out.println("完成业务    " + info);
        return ApiResult.success("suc");
    }
}
