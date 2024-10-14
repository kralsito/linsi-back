package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.dto.response.AreaxmemberDTO;
import com.linsi_backend.linsi_backend.service.AreaxmemberService;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areaxmember")
@Tag(name = "Areaxmember", description = "Areaxmember Endpoints")
public class AreaxmemberController {

    @Autowired
    private AreaxmemberService areaxmemberService;

    @PostMapping
    @Operation(summary = "Asocia un integrante a un area", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<AreaxmemberDTO> createAreaxmember(@RequestBody AreaxmemberDTOin dto) {
        AreaxmemberDTO createdRelation = areaxmemberService.createAreaxmember(dto);
        return ResponseEntity.ok(createdRelation);
    }

    @GetMapping("/area/{areaId}/members")
    @Operation(summary = "Obtiene todos los integrantes de un área")
    public ResponseEntity<List<MemberDTO>> getMembersByArea(@PathVariable Long areaId) {
        List<MemberDTO> members = areaxmemberService.findMembersByArea(areaId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/member/{memberId}/areas")
    @Operation(summary = "Obtiene todas las áreas a las que pertenece un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<List<AreaDTO>> getAreasByMember(@PathVariable Long memberId) {
        List<AreaDTO> areas = areaxmemberService.findAreasByMember(memberId);
        return ResponseEntity.ok(areas);
    }


    @DeleteMapping("/area/{areaId}/member/{memberId}")
    @Operation(summary = "Elimina un integrante de un área", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<Void> removeMemberFromArea(@PathVariable Long areaId, @PathVariable Long memberId) {
        areaxmemberService.removeMemberFromArea(memberId, areaId);
        return ResponseEntity.noContent().build();
    }

}

