package com.linsi_backend.linsi_backend.service.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsFilterDTO {
    private String title;
    private Long user_id;
    private LocalDateTime publicationDate;
}
