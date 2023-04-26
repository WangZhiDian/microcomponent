package com.meng.msg.response;

/**
 * i http error response info
 *
 * @author : sunyuecheng
 */
public interface IHttpErrorResponseMsgInfo {

    /**
     * set error code
     *
     * @param errorCode :
     */
    void setErrorCode(String errorCode);

    /**
     * set error description
     *
     * @param errorDescription :
     */
    void setErrorDescription(String errorDescription);
}
