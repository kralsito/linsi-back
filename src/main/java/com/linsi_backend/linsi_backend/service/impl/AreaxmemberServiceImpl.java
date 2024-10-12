package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.model.*;
import com.linsi_backend.linsi_backend.repository.*;
import com.linsi_backend.linsi_backend.service.AreaxmemberService;
import com.linsi_backend.linsi_backend.service.ImageService;
import com.linsi_backend.linsi_backend.service.dto.request.AreaxmemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.AreaDTO;
import com.linsi_backend.linsi_backend.service.dto.response.AreaxmemberDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.mapper.AreaMapper;
import com.linsi_backend.linsi_backend.service.mapper.AreaxmemberMapper;
import com.linsi_backend.linsi_backend.service.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.linsi_backend.linsi_backend.repository.AreaxmemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AreaxmemberServiceImpl implements AreaxmemberService {

    private final AreaxmemberRepository areaxmemberRepository;

    private final MemberRepository memberRepository;

    private final AreaRepository areaRepository;

    private final ImageService imageService;

    @Override
    public AreaxmemberDTO createAreaxmember(AreaxmemberDTOin dto) {
        Member member = getMember(dto);
        Area area = getArea(dto);
        Optional<Areaxmember> existingAreaxmember = areaxmemberRepository.findByMemberAndArea(member, area);
        if (existingAreaxmember.isPresent()) {
            throw new BadRequestException(Error.MEMBER_ALREADY_IN_AREA);
        }
        Areaxmember areaxmember = AreaxmemberMapper.MAPPER.toEntity(dto);
        areaxmember.setMember(member);
        areaxmember.setArea(area);
        areaxmember = areaxmemberRepository.save(areaxmember);
        AreaxmemberDTO areaxmemberDTO = AreaxmemberMapper.MAPPER.toDto(areaxmember);
        return areaxmemberDTO;
    }

    @Override
    public List<MemberDTO> findMembersByArea(Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(Error.AREA_NOT_FOUND));

        List<Areaxmember> areaxmembers = areaxmemberRepository.findByArea(area);

        List<Member> memberList = areaxmembers.stream()
                                  .map(Areaxmember::getMember)
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
    public List<AreaDTO> findAreasByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(Error.MEMBER_NOT_FOUND));

        List<Areaxmember> areaxmembers = areaxmemberRepository.findByMember(member);

        return areaxmembers.stream()
                .map(areaxmember -> AreaMapper.MAPPER.toDto(areaxmember.getArea()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMemberFromArea(Long memberId, Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BadRequestException(Error.AREA_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(Error.MEMBER_NOT_FOUND));

        Areaxmember areaxmember = areaxmemberRepository.findByMemberAndArea(member, area)
                .orElseThrow(() -> new BadRequestException(Error.RELATION_NOT_FOUND));

        areaxmemberRepository.delete(areaxmember);
    }


    private Member getMember(AreaxmemberDTOin dto) {
        Optional<Member> memberOptional = memberRepository.findById(dto.getMember_id());
        if (memberOptional.isEmpty()) {
            throw new BadRequestException(Error.MEMBER_NOT_FOUND);
        }
        Member member = memberOptional.get();
        member.setId(dto.getMember_id());
        return member;
    }

    private Area getArea(AreaxmemberDTOin dto) {
        Optional<Area> areaOptional = areaRepository.findById(dto.getArea_id());
        if (areaOptional.isEmpty()) {
            throw new BadRequestException(Error.AREA_NOT_FOUND);
        }
        Area area = areaOptional.get();
        area.setId(dto.getArea_id());
        return area;
    }
}
