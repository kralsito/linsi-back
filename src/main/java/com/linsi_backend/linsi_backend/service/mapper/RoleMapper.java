package com.linsi_backend.linsi_backend.service.mapper;


import com.linsi_backend.linsi_backend.model.Role;
import com.linsi_backend.linsi_backend.service.dto.request.RoleDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);
    Role toEntity(RoleDTOin dto);
}
