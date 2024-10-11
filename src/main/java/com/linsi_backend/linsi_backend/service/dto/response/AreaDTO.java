package com.linsi_backend.linsi_backend.service.dto.response;

import lombok.Data;

@Data
public class AreaDTO {
    private Long id;
    private String name;
    private UserDTO user;
}
