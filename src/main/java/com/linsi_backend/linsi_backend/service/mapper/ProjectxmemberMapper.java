package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Areaxmember;
import com.linsi_backend.linsi_backend.model.Projectxmember;
import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectxmemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectxmemberMapper extends EntityMapper<ProjectxmemberDTO, Projectxmember>{
    ProjectxmemberMapper MAPPER = Mappers.getMapper(ProjectxmemberMapper.class);
    Projectxmember toEntity(ProjectxmemberDTOin dto);
}
