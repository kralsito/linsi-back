package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.NewsRepository;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.repository.specification.NewsSpec;
import com.linsi_backend.linsi_backend.service.ImageService;
import com.linsi_backend.linsi_backend.service.NewsService;
import com.linsi_backend.linsi_backend.service.dto.request.NewsDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.NewsFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.NewsDTO;
import com.linsi_backend.linsi_backend.service.mapper.MemberMapper;
import com.linsi_backend.linsi_backend.service.mapper.NewsMapper;
import com.linsi_backend.linsi_backend.util.AuthSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, ImageService imageService) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Override
    public NewsDTO create(NewsDTOin dto) {
        Long userId = AuthSupport.getUserId();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        News news = NewsMapper.MAPPER.toEntity(dto);
        news.setUser(user.get());
        news = newsRepository.save(news);
        Long imageId = imageService.uploadImage(dto.getImage(), ImageType.NEWS, news.getId());
        news.setImageId(imageId);
        news = newsRepository.save(news);
        NewsDTO newsDTO = NewsMapper.MAPPER.toDto(news);
        newsDTO.setS3Url(imageService.getS3url(news.getId(), ImageType.NEWS));
        return newsDTO;
    }

    @Override
    public NewsDTO getById(Long id) {
        News news = getNews(id);
        NewsDTO dto = NewsMapper.MAPPER.toDto(news);
        String s3Url = imageService.getS3url(id, ImageType.NEWS);
        dto.setS3Url(s3Url);
        return dto;
    }

    @Override
    public Page<NewsDTO> getAll(NewsFilterDTO filter, Pageable pageable) {
        Specification<News> spec = NewsSpec.getSpec(filter);
        Page<News> page = newsRepository.findAll(spec, pageable);
        Page<NewsDTO> dtoPage = page.map(news -> {
            try {
                NewsDTO dto = NewsMapper.MAPPER.toDto(news);
                String s3Url = imageService.getS3url(news.getId(), ImageType.NEWS);
                dto.setS3Url(s3Url);
                return dto;
            } catch (Exception e) {
                System.err.println("Error al obtener la URL de la imagen para ID " + news.getId() + ": " + e.getMessage());
                NewsDTO dto = NewsMapper.MAPPER.toDto(news);
                dto.setS3Url(null);
                return dto;
            }
        });
        return dtoPage;
    }

    @Override
    public NewsDTO update(Long id, NewsDTOin dto) {
        News news = getNews(id);
        News newsUpdated = NewsMapper.MAPPER.toEntity(dto);
        NewsMapper.MAPPER.update(news, newsUpdated);
        if (dto.getImage() != null) {
            imageService.deleteImage(news.getId(), ImageType.NEWS);
            Long imageId = imageService.uploadImage(dto.getImage(), ImageType.NEWS, news.getId());
            news.setImageId(imageId);
        }
        newsRepository.save(news);
        NewsDTO newsDTO = NewsMapper.MAPPER.toDto(news);
        newsDTO.setS3Url(imageService.getS3url(news.getId(), ImageType.NEWS));
        return newsDTO;
    }

    @Override
    public void delete(Long id) {
        Long userId = AuthSupport.getUserId();
        News news = getNews(id);
        if (userId == null) {
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        newsRepository.delete(news);
    }

    private News getNews(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            throw new BadRequestException(Error.NEWS_NOT_FOUND);
        }
        return newsOptional.get();
    }
}
