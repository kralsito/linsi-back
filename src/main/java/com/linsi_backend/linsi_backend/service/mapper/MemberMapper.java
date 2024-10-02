package com.linsi_backend.linsi_backend.service.mapper;


import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper extends EntityMapper<MemberDTO, Member>{
    MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);
    Member toEntity(MemberDTOin dto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "imageId")
    void update(@MappingTarget Member entity, Member updateEntity);
}