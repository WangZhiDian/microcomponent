package com.meng.exception.handler;

import com.meng.bean.ApiResult;
import com.meng.exception.except.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    * 处理所有未指定的异常
    * */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(Exception e, HttpServletRequest request,
                                             HttpServletResponse response) {

        String requestURI = request.getRequestURI();
        log.error("请求路径:" + requestURI + " 失败: " + e.toString());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ApiResult.error("系统异常，请联系管理员!");
    }

    /*
    * 处理业务指定异常 ServiceException
    * */
    @ExceptionHandler(ServiceException.class)
    public ApiResult<String> handleServiceException(ServiceException except,
                                                    HttpServletResponse response) {
        String code = except.getInfo().getCode();
        String msg = except.getInfo().getMsg();
        response.setStatus(except.getHttpStatus());
        return ApiResult.fail(code, msg);
    }


}
