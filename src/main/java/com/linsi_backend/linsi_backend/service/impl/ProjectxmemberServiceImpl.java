package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.*;
import com.linsi_backend.linsi_backend.service.ImageService;
import com.linsi_backend.linsi_backend.service.ProjectxmemberService;
import com.linsi_backend.linsi_backend.service.dto.request.ProjectxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.*;
import com.linsi_backend.linsi_backend.service.mapper.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectxmemberServiceImpl implements ProjectxmemberService {
    private final ProjectxmemberRepository projectxmemberRepository;

    private final MemberRepository memberRepository;

    private final ProjectRepository projectRepository;

    private final ImageService imageService;

    @Override
    public ProjectxmemberDTO createProjectxmember(ProjectxmemberDTOin dto) {
        Member member = getMember(dto);
        Project project = getProject(dto);
        Optional<Projectxmember> existingProjectxmember = projectxmemberRepository.findByMemberAndProject(member, project);
        if (existingProjectxmember.isPresent()) {
            throw new BadRequestException(Error.MEMBER_ALREADY_IN_PROJECT);
        }
        Projectxmember projectxmember = ProjectxmemberMapper.MAPPER.toEntity(dto);
        projectxmember.setMember(member);
        projectxmember.setProject(project);
        projectxmember = projectxmemberRepository.save(projectxmember);
        ProjectxmemberDTO projectxmemberDTO = ProjectxmemberMapper.MAPPER.toDto(projectxmember);
        return projectxmemberDTO;
    }

    @Override
    public List<MemberDTO> findMembersByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException(Error.PROJECT_NOT_FOUND));

        List<Projectxmember> projectxmembers = projectxmemberRepository.findByProject(project);

        List<Member> memberList = projectxmembers.stream()
                .map(Projectxmember::getMember)
                .collect(Collectors.toList());
        List<MemberDTO> memberDTOS = MemberMapper.MAPPER.toDto(memberList);
        memberDTOS.forEach(dto -> {
            try {
                String s3Url = imageService.getS3url(dto.getId(), ImageType.MEMBER);
                dto.setS3Url(s3Url);
            } catch (Exception e) {
                System.err.println("Error al obtener la URL de la imagen para ID " + dto.getId() + ": " + e.getMessage());
                dto.setS3Url(null);
            }
        });
        return memberDTOS;
    }

    @Override
    public List<ProjectDTO> findProjectsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(Error.MEMBER_NOT_FOUND));

        List<Projectxmember> projectxmembers = projectxmemberRepository.findByMember(member);

        return projectxmembers.stream()
                .map(projectxmember -> ProjectMapper.MAPPER.toDto(projectxmember.getProject()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMemberFromProject(Long memberId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException(Error.PROJECT_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(Error.MEMBER_NOT_FOUND));

        Projectxmember projectxmember = projectxmemberRepository.findByMemberAndProject(member, project)
                .orElseThrow(() -> new BadRequestException(Error.RELATION_PROJECT_MEMBER_NOT_FOUND));

        projectxmemberRepository.delete(projectxmember);
    }


    private Member getMember(ProjectxmemberDTOin dto) {
        Optional<Member> memberOptional = memberRepository.findById(dto.getMember_id());
        if (memberOptional.isEmpty()) {
            throw new BadRequestException(Error.MEMBER_NOT_FOUND);
        }
        Member member = memberOptional.get();
        member.setId(dto.getMember_id());
        return member;
    }

    private Project getProject(ProjectxmemberDTOin dto) {
        Optional<Project> projectOptional = projectRepository.findById(dto.getProject_id());
        if (projectOptional.isEmpty()) {
            throw new BadRequestException(Error.PROJECT_NOT_FOUND);
        }
        Project project = projectOptional.get();
        project.setId(dto.getProject_id());
        return project;
    }
}
