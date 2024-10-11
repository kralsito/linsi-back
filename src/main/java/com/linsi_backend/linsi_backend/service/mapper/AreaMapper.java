package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Area;
import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.service.dto.request.AreaDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AreaMapper extends EntityMapper<AreaDTO, Area> {
    AreaMapper MAPPER = Mappers.getMapper(AreaMapper.class);
    Area toEntity(AreaDTOin dto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "user")
    void update(@MappingTarget Area entity, Area updateEntity);
}

