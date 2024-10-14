package com.linsi_backend.linsi_backend.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Data
public class ProjectFilterDTO {
    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private Long user_id;
}
