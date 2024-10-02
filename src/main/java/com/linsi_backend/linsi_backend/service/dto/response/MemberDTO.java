package com.linsi_backend.linsi_backend.service.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserDTO user;
    private String s3Url;
    private RoleDTO role;
}
