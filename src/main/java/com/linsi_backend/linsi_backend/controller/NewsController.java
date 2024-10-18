package com.linsi_backend.linsi_backend.controller;

import com.linsi_backend.linsi_backend.service.NewsService;
import com.linsi_backend.linsi_backend.service.dto.request.NewsDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.NewsFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.NewsDTO;
import com.linsi_backend.linsi_backend.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/news")
@Tag(name = "News", description = "News Endpoints")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crea una noticia", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<NewsDTO> create(@RequestParam String title,
                                          @RequestParam String description,
                                          @RequestParam (required = false) MultipartFile image) {
        NewsDTOin dto = new NewsDTOin(title, description, image);
        NewsDTO response = newsService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtiene todas las noticias, con filtro")
    public ResponseEntity<List<NewsDTO>> getAll(@ParameterObject NewsFilterDTO filterDTO, @ParameterObject Pageable pageable) {
        Page<NewsDTO> response = newsService.getAll(filterDTO, pageable);
        HttpHeaders headers = PaginationUtil.setTotalCountPageHttpHeaders(response);
        return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una noticia por id", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<NewsDTO> getById(@PathVariable Long id) {
        NewsDTO response = newsService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifica una noticia", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<NewsDTO> update(@PathVariable Long id,
                                          @RequestParam String title,
                                          @RequestParam String description,
                                          @RequestParam(required = false) MultipartFile image,
                                          @RequestParam Long userId) {
        NewsDTOin dto = new NewsDTOin(title, description, image, userId);
        NewsDTO response = newsService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una noticia", security = { @SecurityRequirement(name = "bearer-jwt") })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
