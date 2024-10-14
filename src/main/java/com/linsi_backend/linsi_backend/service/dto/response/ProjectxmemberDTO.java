package com.linsi_backend.linsi_backend.service.dto.response;

import lombok.Data;

@Data
public class ProjectxmemberDTO {
    private Long id;
    private ProjectDTO project;
    private MemberDTO member;
}
