package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.RegistrationService;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.request.RegistrationStatusDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.RegistrationDTO;
import com.linsi_backend.linsi_backend.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
@Tag(name = "Registration", description = "Registration Endpoints")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    @Operation(summary = "Crea una inscripción")
    public ResponseEntity<RegistrationDTO> create(@RequestParam String firstName,
                                                  @RequestParam String lastName,
                                                  @RequestParam int dni,
                                                  @RequestParam int file,
                                                  @RequestParam String universityYear,
                                                  @RequestParam String email,
                                                  @RequestParam Long area_id){
        RegistrationDTOin dto = new RegistrationDTOin(firstName, lastName, dni, file, universityYear, email, area_id);
        RegistrationDTO response =  registrationService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtiene las inscripciones, con filtro", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<List<RegistrationDTO>> getAll(@ParameterObject RegistrationFilterDTO filterDTO, @ParameterObject Pageable pageable){
        Page<RegistrationDTO> response = registrationService.getAll(filterDTO, pageable);
        HttpHeaders headers = PaginationUtil.setTotalCountPageHttpHeaders(response);
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una inscripción por id", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<RegistrationDTO> getById(@PathVariable Long id) {
        RegistrationDTO response = registrationService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-status")
    @Operation(summary = "Cambia el estado de una inscripción a confirmada", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<Void> changeStatus(@RequestBody RegistrationStatusDTOin dtoIn) {
        registrationService.changeStatus(dtoIn);
        return ResponseEntity.ok().build();
    }
}
