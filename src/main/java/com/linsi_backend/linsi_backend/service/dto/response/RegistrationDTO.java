package com.linsi_backend.linsi_backend.service.dto.response;

import com.linsi_backend.linsi_backend.model.RegistrationStatusType;
import lombok.Data;

@Data
public class RegistrationDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private int file;
    private String universityYear;
    private String email;
    private AreaDTO area;
    private RegistrationStatusType registrationStatusType;
}
