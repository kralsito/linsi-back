package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.RegistrationDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationStatusDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.RegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegistrationService {

    RegistrationDTO create(RegistrationDTOin dto);

    RegistrationDTO getById(Long id);

    Page<RegistrationDTO> getAll(RegistrationFilterDTO filter, Pageable pageable);

    void changeStatus(RegistrationStatusDTOin dtoIn);
}
