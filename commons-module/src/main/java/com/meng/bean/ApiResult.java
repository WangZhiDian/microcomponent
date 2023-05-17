package com.meng.bean;


import com.meng.consts.CommonConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T> implements Serializable {


    public String code;

    public String msg;

    public T data;

    public static ApiResult<String> error(String info) {
        return restResult(info, CommonConstants.FAIL, "");
    }


    public static ApiResult<Object> success(Object object) {
        return restResult(object, CommonConstants.SUCCESS, "");
    }

    public ApiResult<T> success2(T data) {
        return restResult(data, CommonConstants.SUCCESS, "");
    }


    public static ApiResult<String> fail(String code, String msg) {
        return restResult("", code, msg);
    }

    private static <T> ApiResult<T> restResult(T data, String code, String msg) {
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
