package com.linsi_backend.linsi_backend.exception.custom;


import com.linsi_backend.linsi_backend.exception.error.ErrorCode;

public class BadRequestException extends ServiceException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(String code, String message) {
        super(code, message);
    }
}
