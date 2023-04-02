package com.meng.exception.code;

public enum ExceptionCode {


    OK("200", "正常"),
    EXCEPT_TEST_MATH("100001", "除法异常，被除数不能为 0"),
    EXCEPT_TEST_NULL("100002", "对象不能为空"),
    EXCEPT_AUTH_NO_REQUEST("100100", "没有request信息");


    private final String code;
    private final String msg;

    ExceptionCode(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }

}
