package com.linsi_backend.linsi_backend.service;


import com.linsi_backend.linsi_backend.service.dto.request.UserDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.UserDTO;

public interface AuthService {

    /**
     * Registra un usuario
     * @param dto
     */
    void register(UserDTOin dto);

    /**
     * Autentica al usuario
     * @param dto
     * @return
     */
    UserDTO authenticate(UserDTOin dto);
}
