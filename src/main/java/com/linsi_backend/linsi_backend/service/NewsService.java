package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.NewsDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.NewsFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsDTO create(NewsDTOin dto);

    NewsDTO getById(Long id);

    Page<NewsDTO> getAll(NewsFilterDTO filter, Pageable pageable);

    NewsDTO update(Long id, NewsDTOin dto);

    void delete(Long id);
}
