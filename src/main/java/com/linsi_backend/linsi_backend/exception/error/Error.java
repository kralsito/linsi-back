package com.linsi_backend.linsi_backend.exception.error;

public enum Error implements ErrorCode {
    AUTH_ERROR("0001", "Error al iniciar sesion"),


    ;

    private final String code;

    private final String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}
