package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.ProjectDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectDTO create(ProjectDTOin dto);

    Page<ProjectDTO> getAll(ProjectFilterDTO filter, Pageable pageable);

    ProjectDTO getById(Long id);

    ProjectDTO update(Long id, ProjectDTOin dto);

    void delete(Long id);
}
