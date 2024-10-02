package com.linsi_backend.linsi_backend.repository;


import com.linsi_backend.linsi_backend.model.Image;
import com.linsi_backend.linsi_backend.model.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByModelIdAndType(@Param("modelId") Long modelId, @Param("type") ImageType imageType);
}
