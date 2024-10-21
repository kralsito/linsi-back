package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.ProjectService;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import com.linsi_backend.linsi_backend.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/project")
@Tag(name = "Project", description = "Project Endpoints")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(summary = "Crea un proyecto", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<ProjectDTO> create(@RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        ProjectDTOin dto = new ProjectDTOin(name, description, startDate, endDate);
        ProjectDTO response = projectService.create(dto);
        return ResponseEntity.ok(response);
    }

    //TODO: los filtros por fecha no andan bien por el formato
    @GetMapping
    @Operation(summary = "Obtiene integrantes, con filtro")
    public ResponseEntity<List<ProjectDTO>> getAll(@ParameterObject ProjectFilterDTO filterDTO, @ParameterObject Pageable pageable){
        Page<ProjectDTO> response = projectService.getAll(filterDTO, pageable);
        HttpHeaders headers = PaginationUtil.setTotalCountPageHttpHeaders(response);
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los proyectos por id")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        ProjectDTO response = projectService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Modifica un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id,
                                             @RequestParam String name,
                                             @RequestParam String description,
                                             @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        ProjectDTOin dto = new ProjectDTOin(name, description, startDate, endDate);
        ProjectDTO response = projectService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un proyecto", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<String> delete(@PathVariable Long id)  {
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
