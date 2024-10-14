package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Project;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project>{
    ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);
    Project toEntity(ProjectDTOin dto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "user")
    void update(@MappingTarget Project entity, Project updateEntity);
}
