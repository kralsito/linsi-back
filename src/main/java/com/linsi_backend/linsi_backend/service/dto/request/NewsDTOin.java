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
    private Long userId;

    // Constructor sin userId, para cuando solo creas la noticia sin asignar usuario
    public NewsDTOin(String title, String description, MultipartFile image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
}
