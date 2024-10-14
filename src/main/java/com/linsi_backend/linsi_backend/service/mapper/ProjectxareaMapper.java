package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Projectxarea;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxareaDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectxareaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectxareaMapper extends EntityMapper<ProjectxareaDTO, Projectxarea>{
    ProjectxareaMapper MAPPER = Mappers.getMapper(ProjectxareaMapper.class);
    Projectxarea toEntity(ProjectxareaDTOin dto);
}
