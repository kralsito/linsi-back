package com.linsi_backend.linsi_backend.controller;


import com.linsi_backend.linsi_backend.service.RoleService;
import com.linsi_backend.linsi_backend.service.dto.request.RoleDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RoleFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Tag(name = "Role", description = "Role Endpoints")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService provinceService) {
        this.roleService = provinceService;
    }

    @PostMapping
    @Operation(summary = "Crea un rol")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTOin dto){
        RoleDTO response =  roleService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtiene los roles, con filtro")
    public ResponseEntity<List<RoleDTO>> getAll(@ParameterObject RoleFilterDTO filterDTO, @ParameterObject Pageable pageable){
        Page<RoleDTO> response = roleService.getAll(filterDTO, pageable);
        return new ResponseEntity<>(response.getContent(), HttpStatus.OK);
    }
}
