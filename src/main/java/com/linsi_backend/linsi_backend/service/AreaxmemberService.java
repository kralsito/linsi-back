package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.dto.response.AreaxmemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;

import java.util.List;

public interface AreaxmemberService {
    AreaxmemberDTO createAreaxmember(AreaxmemberDTOin dto);
    List<MemberDTO> findMembersByArea(Long areaId);
    List<AreaDTO> findAreasByMember(Long memberId);
    void removeMemberFromArea(Long memberId, Long areaId);
}
