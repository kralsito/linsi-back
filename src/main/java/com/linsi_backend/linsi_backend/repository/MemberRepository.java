package com.linsi_backend.linsi_backend.repository;


import com.linsi_backend.linsi_backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    List<Member> findByIdIn(List<Long> ids);
}
