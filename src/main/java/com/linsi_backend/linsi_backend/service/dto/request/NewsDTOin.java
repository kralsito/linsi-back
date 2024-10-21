package com.linsi_backend.linsi_backend.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTOin {
    private String title;
    private String description;
    private MultipartFile image;
}
