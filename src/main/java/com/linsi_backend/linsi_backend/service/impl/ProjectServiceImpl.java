package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.ProjectRepository;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.repository.specification.ProjectSpec;
import com.linsi_backend.linsi_backend.service.ProjectService;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import com.linsi_backend.linsi_backend.service.mapper.MemberMapper;
import com.linsi_backend.linsi_backend.service.mapper.ProjectMapper;
import com.linsi_backend.linsi_backend.util.AuthSupport;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    @Override
    public ProjectDTO create(ProjectDTOin dto) {
        Long userId = AuthSupport.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        Project project =  ProjectMapper.MAPPER.toEntity(dto);
        project.setUser(user.get());
        project = projectRepository.save(project);
        return ProjectMapper.MAPPER.toDto(project);
    }

    @Override
    public Page<ProjectDTO> getAll(ProjectFilterDTO filter, Pageable pageable) {
        Specification<Project> spec = ProjectSpec.getSpec(filter);
        Page<Project> page = projectRepository.findAll(spec, pageable);
        return  page.map(ProjectMapper.MAPPER::toDto);
    }

    @Override
    public ProjectDTO getById(Long id) {
        Project project = getProject(id);
        return ProjectMapper.MAPPER.toDto(project);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTOin dto) {
        Project project = getProject(id);
        Project projectUpdated = ProjectMapper.MAPPER.toEntity(dto);
        ProjectMapper.MAPPER.update(project, projectUpdated);
        projectRepository.save(project);
        return ProjectMapper.MAPPER.toDto(project);
    }

    @Override
    public void delete(Long id)  {
        Long userId = AuthSupport.getUserId();
        Project project = getProject(id);
        if(userId == null){
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        projectRepository.delete(project);
    }

    private Project getProject(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isEmpty()) {
            throw new BadRequestException(Error.PROJECT_NOT_FOUND);
        }
        return projectOptional.get();
    }
}
