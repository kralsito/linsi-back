package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.Registration;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.RegistrationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RegistrationMapper extends EntityMapper<RegistrationDTO, Registration> {
    RegistrationMapper MAPPER = Mappers.getMapper(RegistrationMapper.class);
    Registration toEntity(RegistrationDTOin dto);
}
