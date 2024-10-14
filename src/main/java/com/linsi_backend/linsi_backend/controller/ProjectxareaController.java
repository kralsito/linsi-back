package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.ProjectxareaService;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxareaDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectxarea")
@Tag(name = "Projectxarea", description = "Projectxarea Endpoints")
public class ProjectxareaController {

    @Autowired
    private ProjectxareaService projectxareaService;

    @PostMapping
    @Operation(summary = "Asocia un proyecto a un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<ProjectxareaDTO> createProjectxarea(@RequestBody ProjectxareaDTOin dto) {
        ProjectxareaDTO createdRelation = projectxareaService.createProjectxarea(dto);
        return ResponseEntity.ok(createdRelation);
    }

    @GetMapping("/project/{projectId}/areas")
    @Operation(summary = "Obtiene todas las areas que participan de un proyecto")
    public ResponseEntity<List<AreaDTO>> getAreasByProject(@PathVariable Long projectId) {
        List<AreaDTO> areas = projectxareaService.findAreasByProject(projectId);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/area/{areaId}/projects")
    @Operation(summary = "Obtiene todos los proyectos en los que participa un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<List<ProjectDTO>> getProjectsByArea(@PathVariable Long areaId) {
        List<ProjectDTO> projects = projectxareaService.findProjectsByArea(areaId);
        return ResponseEntity.ok(projects);
    }


    @DeleteMapping("/project/{projectId}/area/{areaId}")
    @Operation(summary = "Elimina un proyecto de un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<Void> removeProjectFromArea(@PathVariable Long projectId, @PathVariable Long areaId) {
        projectxareaService.removeAreaFromProject(areaId, projectId);
        return ResponseEntity.noContent().build();
    }
}
