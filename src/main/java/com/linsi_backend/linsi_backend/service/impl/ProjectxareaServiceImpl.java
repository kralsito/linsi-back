package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.AreaRepository;
import com.linsi_backend.linsi_backend.repository.ProjectRepository;
import com.linsi_backend.linsi_backend.repository.ProjectxareaRepository;
import com.linsi_backend.linsi_backend.service.ProjectxareaService;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxareaDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectxareaDTO;
import com.linsi_backend.linsi_backend.service.mapper.AreaMapper;
import com.linsi_backend.linsi_backend.service.mapper.ProjectMapper;
import com.linsi_backend.linsi_backend.service.mapper.ProjectxareaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectxareaServiceImpl implements ProjectxareaService {
    private final ProjectxareaRepository projectxareaRepository;

    private final AreaRepository areaRepository;

    private final ProjectRepository projectRepository;

    @Override
    public ProjectxareaDTO createProjectxarea(ProjectxareaDTOin dto) {
        Area area = getArea(dto);
        Project project = getProject(dto);
        Optional<Projectxarea> existingProjectxarea = projectxareaRepository.findByAreaAndProject(area, project);
        if (existingProjectxarea.isPresent()) {
            throw new BadRequestException(Error.MEMBER_ALREADY_IN_PROJECT);
        }
        Projectxarea projectxarea = ProjectxareaMapper.MAPPER.toEntity(dto);
        projectxarea.setArea(area);
        projectxarea.setProject(project);
        projectxarea = projectxareaRepository.save(projectxarea);
        return ProjectxareaMapper.MAPPER.toDto(projectxarea);
    }

    @Override
    public List<AreaDTO> findAreasByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException(Error.PROJECT_NOT_FOUND));

        List<Projectxarea> projectxareas = projectxareaRepository.findByProject(project);

        List<Area> areaList = projectxareas.stream()
                .map(Projectxarea::getArea)
                .collect(Collectors.toList());
        return AreaMapper.MAPPER.toDto(areaList);
    }

    @Override
    public List<ProjectDTO> findProjectsByArea(Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(Error.AREA_NOT_FOUND));

        List<Projectxarea> projectxareas = projectxareaRepository.findByArea(area);

        return projectxareas.stream()
                .map(projectxarea -> ProjectMapper.MAPPER.toDto(projectxarea.getProject()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAreaFromProject(Long areaId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException(Error.PROJECT_NOT_FOUND));

        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(Error.AREA_NOT_FOUND));

        Projectxarea projectxarea = projectxareaRepository.findByAreaAndProject(area, project)
                .orElseThrow(() -> new BadRequestException(Error.RELATION_PROJECT_AREA_NOT_FOUND));

        projectxareaRepository.delete(projectxarea);
    }


    private Area getArea(ProjectxareaDTOin dto) {
        Optional<Area> areaOptional = areaRepository.findById(dto.getArea_id());
        if (areaOptional.isEmpty()) {
            throw new BadRequestException(Error.AREA_NOT_FOUND);
        }
        Area area = areaOptional.get();
        area.setId(dto.getArea_id());
        return area;
    }

    private Project getProject(ProjectxareaDTOin dto) {
        Optional<Project> projectOptional = projectRepository.findById(dto.getProject_id());
        if (projectOptional.isEmpty()) {
            throw new BadRequestException(Error.PROJECT_NOT_FOUND);
        }
        Project project = projectOptional.get();
        project.setId(dto.getProject_id());
        return project;
    }

}
