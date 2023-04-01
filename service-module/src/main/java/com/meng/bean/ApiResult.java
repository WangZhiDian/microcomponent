package com.meng.bean;

public class ApiResult {


    public String error;

    public Object data;

    public static ApiResult error(String error) {
        return new ApiResult();
    }

    public static ApiResult success(String info) {
        return new ApiResult();
    }

    public static ApiResult success(Object object) {
        ApiResult apiResult = new ApiResult();
        apiResult.data = object;
        return apiResult;
    }

}
