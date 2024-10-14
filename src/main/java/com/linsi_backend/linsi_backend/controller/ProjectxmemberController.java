package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.ProjectxmemberService;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectDTO;
import com.linsi_backend.linsi_backend.service.dto.response.ProjectxmemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectxmember")
@Tag(name = "Projectxmember", description = "Projectxmember Endpoints")
public class ProjectxmemberController {

    @Autowired
    private ProjectxmemberService projectxmemberService;

    @PostMapping
    @Operation(summary = "Asocia un integrante a un proyecto", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<ProjectxmemberDTO> createProjectxmember(@RequestBody ProjectxmemberDTOin dto) {
        ProjectxmemberDTO createdRelation = projectxmemberService.createProjectxmember(dto);
        return ResponseEntity.ok(createdRelation);
    }

    @GetMapping("/project/{projectId}/members")
    @Operation(summary = "Obtiene todos los integrantes de un proyecto")
    public ResponseEntity<List<MemberDTO>> getMembersByProject(@PathVariable Long projectId) {
        List<MemberDTO> members = projectxmemberService.findMembersByProject(projectId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/member/{memberId}/projects")
    @Operation(summary = "Obtiene todas los proyectos a los que pertenece un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<List<ProjectDTO>> getProjectsByMember(@PathVariable Long memberId) {
        List<ProjectDTO> projects = projectxmemberService.findProjectsByMember(memberId);
        return ResponseEntity.ok(projects);
    }


    @DeleteMapping("/project/{projectId}/member/{memberId}")
    @Operation(summary = "Elimina un integrante de un proyecto", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<Void> removeMemberFromProject(@PathVariable Long projectId, @PathVariable Long memberId) {
        projectxmemberService.removeMemberFromProject(memberId, projectId);
        return ResponseEntity.noContent().build();
    }
}
