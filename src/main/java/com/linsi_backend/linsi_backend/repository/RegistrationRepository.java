package com.linsi_backend.linsi_backend.repository;

import com.linsi_backend.linsi_backend.model.Area;
import com.linsi_backend.linsi_backend.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long>, JpaSpecificationExecutor<Registration> {
    List<Registration> findByArea(Area area);
}
