package com.linsi_backend.linsi_backend.service.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String description;
    private UserDTO user;
    private LocalDateTime publicationDate;
    private String s3Url;
}
