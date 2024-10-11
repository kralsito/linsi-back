package com.linsi_backend.linsi_backend.service.impl;


import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.AreaRepository;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.repository.specification.AreaSpec;
import com.linsi_backend.linsi_backend.service.AreaService;
import com.linsi_backend.linsi_backend.service.dto.request.AreaDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.AreaFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.mapper.AreaMapper;
import com.linsi_backend.linsi_backend.util.AuthSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    private final UserRepository userRepository;

    public AreaServiceImpl(AreaRepository areaRepository,
                             UserRepository userRepository)
    {
        this.areaRepository = areaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AreaDTO create(AreaDTOin dto) {
        Long userId = AuthSupport.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        Area area =  AreaMapper.MAPPER.toEntity(dto);
        area.setUser(user.get());
        area = areaRepository.save(area);
        AreaDTO areaDTO = AreaMapper.MAPPER.toDto(area);
        return areaDTO;
    }

    @Override
    public AreaDTO getById(Long id) {
        Area area = getArea(id);
        AreaDTO dto = AreaMapper.MAPPER.toDto(area);
        return dto;
    }

    @Override
    public Page<AreaDTO> getAll(AreaFilterDTO filter, Pageable pageable) {
        Specification<Area> spec = AreaSpec.getSpec(filter);
        Page<Area> page = areaRepository.findAll(spec, pageable);
        return  page.map(AreaMapper.MAPPER::toDto);
    }


    @Override
    public AreaDTO update(Long id, AreaDTOin dto) {
        Long userId = AuthSupport.getUserId();
        Area area = getArea(id);
        Area areaUpdated = AreaMapper.MAPPER.toEntity(dto);
        if(userId == null){
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        AreaMapper.MAPPER.update(area, areaUpdated);
        areaRepository.save(area);
        AreaDTO areaDTO = AreaMapper.MAPPER.toDto(area);
        return areaDTO;
    }

    @Override
    public void delete(Long id)  {
        Long userId = AuthSupport.getUserId();
        Area area = getArea(id);
        if(userId == null){
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        areaRepository.delete(area);
    }

    private Area getArea(Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        if (areaOptional.isEmpty()) {
            throw new BadRequestException(Error.AREA_NOT_FOUND);
        }
        return areaOptional.get();
    }

}
