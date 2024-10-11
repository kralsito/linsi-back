package com.linsi_backend.linsi_backend.exception.error;

public enum Error implements ErrorCode {
    AUTH_ERROR("0001", "Error al iniciar sesion"),
    ROLE_NOT_FOUND("0002", "No se encontró el rol"),
    USER_NOT_LOGIN("0003", "No se encontró un usuario"),
    AREA_NOT_FOUND("0004", "No se encontró un area"),
    MEMBER_NOT_FOUND("0005", "No se encontró un integrante"),



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
