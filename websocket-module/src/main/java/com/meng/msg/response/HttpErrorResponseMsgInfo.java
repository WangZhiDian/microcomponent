package com.meng.msg.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * http error response info
 *
 * @author : sunyuecheng
 */
public class HttpErrorResponseMsgInfo implements IHttpErrorResponseMsgInfo {

    /**
     * error code
     */
    @JSONField(name = "error_code")
    private String errorCode = null;

    /**
     * error description
     */
    @JSONField(name = "error_description")
    private String errorDescription = null;

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
