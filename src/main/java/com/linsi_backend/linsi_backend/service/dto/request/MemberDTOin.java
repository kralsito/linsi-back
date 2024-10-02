package com.linsi_backend.linsi_backend.service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class MemberDTOin {
    private String firstName;
    private String lastName;
    private String email;
    private MultipartFile image;
    private Long role_id;
}
