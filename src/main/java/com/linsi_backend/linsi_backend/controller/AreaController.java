package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.AreaService;
import com.linsi_backend.linsi_backend.service.dto.request.AreaDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.AreaFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RoleDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.dto.response.RoleDTO;
import com.linsi_backend.linsi_backend.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/area")
@Tag(name = "Area", description = "Area Endpoints")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    @Operation(summary = "Crea un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<AreaDTO> create(@RequestBody AreaDTOin dto){
        AreaDTO response =  areaService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las areas, con filtro")
    public ResponseEntity<List<AreaDTO>> getAll(@ParameterObject AreaFilterDTO filterDTO, @ParameterObject Pageable pageable){
        Page<AreaDTO> response = areaService.getAll(filterDTO, pageable);
        HttpHeaders headers = PaginationUtil.setTotalCountPageHttpHeaders(response);
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene las areas por id")
    public ResponseEntity<AreaDTO> getById(@PathVariable Long id) {
        AreaDTO response = areaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Modifica un área", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<AreaDTO> update(@PathVariable Long id,
                                          @RequestBody AreaDTOin dto) {
        AreaDTO response = areaService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<String> delete(@PathVariable Long id)  {
        areaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
