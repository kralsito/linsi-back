package com.linsi_backend.linsi_backend.service.dto.request;

import lombok.Data;

import java.time.LocalDate;


@Data
public class NewsFilterDTO {
    private String title;
    private Long user_id;
    private LocalDate publicationDate;
}
