package com.linsi_backend.linsi_backend.repository;


import com.linsi_backend.linsi_backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectxmemberRepository extends JpaRepository<Projectxmember, Long> {

    List<Projectxmember> findByProject(Project project);

    Optional<Projectxmember> findByMemberAndProject(Member member, Project project);

    List<Projectxmember> findByMember(Member member);

}
