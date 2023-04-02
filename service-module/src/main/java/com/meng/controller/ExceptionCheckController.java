package com.meng.controller;

import com.meng.annotation.Log;
import com.meng.annotation.PermissionRole;
import com.meng.bean.ApiResult;
import com.meng.exception.code.ExceptionCode;
import com.meng.exception.except.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExceptionCheckController {

    @PermissionRole(roles = "hotel_admin")
    @Log(title = "my_title_test")
    @GetMapping(value = "/info/error/{type}")
    public ApiResult getError(@PathVariable("type") String type, @RequestParam("aa") String aa) throws ServiceException {
        log.info("type:{}, aa:{}", type, aa);
        if ("suc".equals(type)) {
            log.debug("debug{}", type);
            log.info("info:{}", type);
            log.error("error:{}", type);
            return ApiResult.success("success");
        }
        if ("fail".equals(type)) {
            return ApiResult.error("fail");
        }
        if ("matherror".equals(type)) {
            throw new ServiceException(ExceptionCode.EXCEPT_TEST_MATH);
        }
        if ("valuenull".equals(type)) {
            throw new ServiceException(ExceptionCode.EXCEPT_TEST_NULL);
        }
        if ("mathzero".equals(type)) {
            int a = 100;
            int b = 0;
            int result = a / b;
        }
        return ApiResult.success("none");
    }


}
