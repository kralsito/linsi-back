package com.linsi_backend.linsi_backend.service.dto.request;

import com.linsi_backend.linsi_backend.model.RegistrationStatusType;
import lombok.Data;

@Data
public class RegistrationFilterDTO {
    private String firstName;
    private String lastName;
    private int dni;
    private int file;
    private String universityYear;
    private String email;
    private Long area_id;
    private RegistrationStatusType registrationStatusType;
    private Boolean confirmed = false;
}
