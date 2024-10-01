package com.linsi_backend.linsi_backend.exception.custom;


import com.linsi_backend.linsi_backend.exception.error.ErrorCode;

public class UnauthorizeException extends ServiceException {

    public UnauthorizeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizeException(String code, String message) {
        super(code, message);
    }
}
