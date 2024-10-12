package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Areaxmember;
import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaxmemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AreaxmemberMapper extends EntityMapper<AreaxmemberDTO, Areaxmember> {
    AreaxmemberMapper MAPPER = Mappers.getMapper(AreaxmemberMapper.class);
    Areaxmember toEntity(AreaxmemberDTOin dto);
}
