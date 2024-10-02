package com.linsi_backend.linsi_backend.controller;


import com.linsi_backend.linsi_backend.service.MemberService;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.MemberFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/member")
@Tag(name = "Member", description = "Member Endpoints")

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crea un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<MemberDTO> create(@RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String email,
                                            @RequestParam MultipartFile image,
                                            @RequestParam Long role_id){
        MemberDTOin dto = new MemberDTOin(firstName, lastName, email, image, role_id);
        MemberDTO response =  memberService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtiene integrantes, con filtro", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<List<MemberDTO>> getAll(@ParameterObject MemberFilterDTO filterDTO, @ParameterObject Pageable pageable){
        Page<MemberDTO> response = memberService.getAll(filterDTO, pageable);
        HttpHeaders headers = PaginationUtil.setTotalCountPageHttpHeaders(response);
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los integrantes por id", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<MemberDTO> getById(@PathVariable Long id) {
        MemberDTO response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifica un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<MemberDTO> update(@PathVariable Long id,
                                             @RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String email,
                                             @RequestParam MultipartFile image,
                                             @RequestParam Long role_id) {
        MemberDTOin dto = new MemberDTOin(firstName, lastName, email, image, role_id);
        MemberDTO response = memberService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un integrante", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<String> delete(@PathVariable Long id)  {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
