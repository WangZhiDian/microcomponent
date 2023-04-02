package com.meng.exception.except;

import com.meng.exception.code.ExceptionCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public final class ServiceException extends Exception{

    private static final long serialVersionUID = 1L;

    private ExceptionCode info;

    private int httpStatus;

    public ServiceException(ExceptionCode info) {
        this.httpStatus = HttpStatus.OK.value();
        this.info = info;
    }

    public ServiceException(int httpStatus, ExceptionCode info) {
        this.httpStatus = httpStatus;
        this.info = info;
    }

}
