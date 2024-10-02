package com.linsi_backend.linsi_backend.service.impl;



import com.linsi_backend.linsi_backend.model.Role;
import com.linsi_backend.linsi_backend.repository.RoleRepository;
import com.linsi_backend.linsi_backend.repository.specification.RoleSpec;
import com.linsi_backend.linsi_backend.service.RoleService;
import com.linsi_backend.linsi_backend.service.dto.request.RoleDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RoleFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.RoleDTO;
import com.linsi_backend.linsi_backend.service.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public RoleDTO create(RoleDTOin dto) {
        Role role = RoleMapper.MAPPER.toEntity(dto);
        role = roleRepository.save(role);
        return RoleMapper.MAPPER.toDto(role);
    }

    @Override
    public Page<RoleDTO> getAll(RoleFilterDTO filter, Pageable pageable) {
        Specification<Role> spec = RoleSpec.getSpec(filter);
        Page<Role> page = roleRepository.findAll(spec, pageable);
        return page.map(RoleMapper.MAPPER::toDto);
    }
}
