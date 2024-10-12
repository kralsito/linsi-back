package com.linsi_backend.linsi_backend.repository;

import com.linsi_backend.linsi_backend.model.Areaxmember;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.linsi_backend.linsi_backend.model.Area;
import com.linsi_backend.linsi_backend.model.Member;

import java.util.List;
import java.util.Optional;


public interface AreaxmemberRepository extends JpaRepository<Areaxmember, Long> {

    List<Areaxmember> findByArea(Area area);

    Optional<Areaxmember> findByMemberAndArea(Member member, Area area);

    List<Areaxmember> findByMember(Member member);


}
