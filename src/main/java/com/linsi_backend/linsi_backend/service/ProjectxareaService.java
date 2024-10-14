package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.ProjectxareaDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.*;

import java.util.List;

public interface ProjectxareaService {
    ProjectxareaDTO createProjectxarea(ProjectxareaDTOin dto);
    List<AreaDTO> findAreasByProject(Long projectId);
    List<ProjectDTO> findProjectsByArea(Long areaId);
    void removeAreaFromProject(Long areaId, Long projectId);
}
