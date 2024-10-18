package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.News;
import com.linsi_backend.linsi_backend.model.User;
import com.linsi_backend.linsi_backend.repository.NewsRepository;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.repository.specification.NewsSpec;
import com.linsi_backend.linsi_backend.service.NewsService;
import com.linsi_backend.linsi_backend.service.dto.request.NewsDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.NewsFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.NewsDTO;
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

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
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
        return NewsMapper.MAPPER.toDto(news);
    }

    @Override
    public NewsDTO getById(Long id) {
        News news = getNews(id);
        return NewsMapper.MAPPER.toDto(news);
    }

    @Override
    public Page<NewsDTO> getAll(NewsFilterDTO filter, Pageable pageable) {
        Specification<News> spec = NewsSpec.getSpec(filter);
        Page<News> page = newsRepository.findAll(spec, pageable);
        return page.map(NewsMapper.MAPPER::toDto);
    }

    @Override
    public NewsDTO update(Long id, NewsDTOin dto) {
        Long userId = AuthSupport.getUserId();
        News news = getNews(id);
        News updatedNews = NewsMapper.MAPPER.toEntity(dto);
        if (userId == null) {
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        NewsMapper.MAPPER.update(news, updatedNews);
        newsRepository.save(news);
        return NewsMapper.MAPPER.toDto(news);
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
