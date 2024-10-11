package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.AreaDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.AreaFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AreaService {
    AreaDTO create(AreaDTOin dto);

    AreaDTO getById(Long id);

    Page<AreaDTO> getAll(AreaFilterDTO filter, Pageable pageable);

    AreaDTO update(Long id, AreaDTOin dto);

    void delete(Long id);
}
