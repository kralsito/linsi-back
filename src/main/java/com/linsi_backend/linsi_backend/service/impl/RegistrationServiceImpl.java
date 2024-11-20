package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.Area;
import com.linsi_backend.linsi_backend.model.Registration;
import com.linsi_backend.linsi_backend.model.RegistrationStatusType;
import com.linsi_backend.linsi_backend.repository.AreaRepository;
import com.linsi_backend.linsi_backend.repository.RegistrationRepository;
import com.linsi_backend.linsi_backend.repository.specification.RegistrationSpec;
import com.linsi_backend.linsi_backend.service.RegistrationService;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationStatusDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.RegistrationDTO;
import com.linsi_backend.linsi_backend.service.mapper.RegistrationMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final AreaRepository areaRepository;

    @Transactional
    @Override
    public RegistrationDTO create(RegistrationDTOin dto){
        Registration registration = RegistrationMapper.MAPPER.toEntity(dto);
        Area area = getArea(dto);
        registration.setArea(area);
        registration = registrationRepository.save(registration);
        return RegistrationMapper.MAPPER.toDto(registration);
    }

    @Override
    public RegistrationDTO getById(Long id) {
        Registration registration = getRegistration(id);
        return RegistrationMapper.MAPPER.toDto(registration);
    }

    @Override
    public Page<RegistrationDTO> getAll(RegistrationFilterDTO filter, Pageable pageable) {
        Specification<Registration> spec = RegistrationSpec.getSpec(filter);
        Page<Registration> page = registrationRepository.findAll(spec, pageable);
        return  page.map(RegistrationMapper.MAPPER::toDto);
    }

    @Override
    public void changeStatus(RegistrationStatusDTOin dtoIn) {
        Registration registration = getRegistration(dtoIn.getRegistrationId());

        if (registration.getRegistrationStatusType() == RegistrationStatusType.CONFIRMED) {
            throw new BadRequestException(Error.INVALID_STATUS_CHANGE);
        }

        registration.setRegistrationStatusType(dtoIn.getNextStatus());

        registrationRepository.save(registration);
    }


    private Registration getRegistration(Long id) {
        Optional<Registration> registrationOptional = registrationRepository.findById(id);
        if(registrationOptional.isEmpty()){
            throw new BadRequestException(Error.REGISTRATION_NOT_FOUND);
        }
        return registrationOptional.get();
    }


    private Area getArea(RegistrationDTOin dto) {
        Optional<Area> areaOptional = areaRepository.findById(dto.getArea_id());
        if (areaOptional.isEmpty()) {
            throw new BadRequestException(Error.AREA_NOT_FOUND);
        }
        Area area = areaOptional.get();
        area.setId(dto.getArea_id());
        return area;
    }
}
