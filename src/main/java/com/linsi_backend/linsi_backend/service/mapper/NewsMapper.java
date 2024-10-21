package com.linsi_backend.linsi_backend.service.mapper;

import com.linsi_backend.linsi_backend.model.News;
import com.linsi_backend.linsi_backend.service.dto.request.NewsDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.NewsDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsMapper {
    NewsMapper MAPPER = Mappers.getMapper(NewsMapper.class);

    News toEntity(NewsDTOin dto);

    NewsDTO toDto(News entity);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "user")
    @Mapping(ignore = true, target = "imageId")
    void update(@MappingTarget News entity, News updatedEntity);
}
