package com.linsi_backend.linsi_backend.service.dto.request;

import com.linsi_backend.linsi_backend.model.RegistrationStatusType;
import lombok.Data;

@Data
public class RegistrationStatusDTOin {
    private RegistrationStatusType nextStatus;
    private Long registrationId;
}
