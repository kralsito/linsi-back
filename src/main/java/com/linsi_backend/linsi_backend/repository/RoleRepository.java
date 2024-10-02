package com.linsi_backend.linsi_backend.repository;


import com.linsi_backend.linsi_backend.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Page<Role> findAll(Specification<Role> spec, Pageable pageable);
}
