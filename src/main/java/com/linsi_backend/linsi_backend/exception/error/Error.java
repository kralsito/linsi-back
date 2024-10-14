package com.linsi_backend.linsi_backend.exception.error;

public enum Error implements ErrorCode {
    AUTH_ERROR("0001", "Error al iniciar sesion"),
    ROLE_NOT_FOUND("0002", "No se encontró el rol"),
    USER_NOT_LOGIN("0003", "No se encontró un usuario"),
    AREA_NOT_FOUND("0004", "No se encontró un area"),
    MEMBER_NOT_FOUND("0005", "No se encontró un integrante"),
    MEMBER_ALREADY_IN_AREA("0006", "El integrante ya se encuentra en el area"),
    RELATION_NOT_FOUND("0007", "No existe relación entre el integrante y el area"),
    PROJECT_NOT_FOUND("0008", "No se encontró el proyecto"),



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
