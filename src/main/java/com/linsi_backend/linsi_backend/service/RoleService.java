package com.linsi_backend.linsi_backend.service;


import com.linsi_backend.linsi_backend.service.dto.request.RoleDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RoleFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleDTO create(RoleDTOin dto);

    Page<RoleDTO> getAll(RoleFilterDTO filterDTO, Pageable pageable);
}
