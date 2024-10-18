package com.linsi_backend.linsi_backend.service.dto.response;

import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String description;
    private UserDTO userId;
    private Long imageId;
}
