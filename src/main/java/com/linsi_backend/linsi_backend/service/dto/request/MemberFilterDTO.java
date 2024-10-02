package com.linsi_backend.linsi_backend.service.dto.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberFilterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long role_id;
    private Long user_id;
}
