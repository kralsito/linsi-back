package com.linsi_backend.linsi_backend.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationDTOin {
    private String firstName;
    private String lastName;
    private int dni;
    private int file;
    private String universityYear;
    private String email;
    private Long area_id;
}
