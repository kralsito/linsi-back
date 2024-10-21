package com.linsi_backend.linsi_backend.service;

import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.*;

import java.util.List;

public interface ProjectxmemberService {
    ProjectxmemberDTO createProjectxmember(ProjectxmemberDTOin dto);
    List<MemberDTO> findMembersByProject(Long projectId);
    List<ProjectDTO> findProjectsByMember(Long memberId);
    void removeMemberFromProject(Long memberId, Long projectId);
}
