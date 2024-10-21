package com.linsi_backend.linsi_backend.repository;

import com.linsi_backend.linsi_backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectxareaRepository extends JpaRepository<Projectxarea, Long> {
    List<Projectxarea> findByProject(Project project);

    Optional<Projectxarea> findByAreaAndProject(Area area, Project project);

    List<Projectxarea> findByArea(Area area);
}
