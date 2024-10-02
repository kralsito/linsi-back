package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.exception.custom.BadRequestException;
import com.linsi_backend.linsi_backend.model.ImageType;
import com.linsi_backend.linsi_backend.model.Member;
import com.linsi_backend.linsi_backend.model.Role;
import com.linsi_backend.linsi_backend.model.User;
import com.linsi_backend.linsi_backend.repository.MemberRepository;
import com.linsi_backend.linsi_backend.repository.RoleRepository;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.repository.specification.MemberSpec;
import com.linsi_backend.linsi_backend.service.ImageService;
import com.linsi_backend.linsi_backend.service.MemberService;
import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.MemberFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import com.linsi_backend.linsi_backend.service.mapper.MemberMapper;
import com.linsi_backend.linsi_backend.util.AuthSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final RoleRepository roleRepository;


    public MemberServiceImpl(MemberRepository memberRepository,
                             UserRepository userRepository,
                             ImageService imageService,
                             RoleRepository roleRepository)
                                {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.roleRepository = roleRepository;
    }

    @Override
    public MemberDTO create(MemberDTOin dto) {
        Long userId = AuthSupport.getUserId();
        Optional<User> user = userRepository.findById(userId);
        Member member =  MemberMapper.MAPPER.toEntity(dto);
        member.setUser(user.get());
        member = memberRepository.save(member);
        Long imageId = imageService.uploadImage(dto.getImage(), ImageType.MEMBER, member.getId());
        member.setImageId(imageId);
        Role role = getRole(dto);
        member.setRole(role);
        member = memberRepository.save(member);
        MemberDTO memberDTO = MemberMapper.MAPPER.toDto(member);
        memberDTO.setS3Url(imageService.getS3url(member.getId(), ImageType.MEMBER));
        return memberDTO;
    }

    @Override
    public MemberDTO getById(Long id) {
        Member member = getMember(id);
        MemberDTO dto = MemberMapper.MAPPER.toDto(member);
        String s3Url = imageService.getS3url(id, ImageType.MEMBER);
        dto.setS3Url(s3Url);
        return dto;
    }

    @Override
    public Page<MemberDTO> getAll(MemberFilterDTO filter, Pageable pageable) {
        Specification<Member> spec = MemberSpec.getSpec(filter);
        Page<Member> page = memberRepository.findAll(spec, pageable);
        Page<MemberDTO> dtoPage = page.map(member -> {
            try {
                MemberDTO dto = MemberMapper.MAPPER.toDto(member);
                String s3Url = imageService.getS3url(member.getId(), ImageType.MEMBER);
                dto.setS3Url(s3Url);
                return dto;
            } catch (Exception e) {
                System.err.println("Error al obtener la URL de la imagen para ID " + member.getId() + ": " + e.getMessage());
                MemberDTO dto = MemberMapper.MAPPER.toDto(member);
                dto.setS3Url(null);
                return dto;
            }
        });

        return dtoPage;
    }


    @Override
    public MemberDTO update(Long id, MemberDTOin dto) {
        Member member = getMember(id);
        Member memberUpdated = MemberMapper.MAPPER.toEntity(dto);
        MemberMapper.MAPPER.update(member, memberUpdated);
        if (dto.getImage() != null) {
            imageService.deleteImage(member.getId(), ImageType.MEMBER);
            Long imageId = imageService.uploadImage(dto.getImage(), ImageType.MEMBER, member.getId());
            member.setImageId(imageId);
            memberRepository.save(member);
        }
        Role role = getRole(dto);
        member.setRole(role);
        memberRepository.save(member);
        MemberDTO memberDTO = MemberMapper.MAPPER.toDto(member);
        memberDTO.setS3Url(imageService.getS3url(member.getId(), ImageType.MEMBER));
        return memberDTO;
    }

    @Override
    public void delete(Long id)  {
        Long userId = AuthSupport.getUserId();
        Member member = getMember(id);
        if(userId == null){
            throw new BadRequestException(Error.USER_NOT_LOGIN);
        }
        memberRepository.delete(member);
    }

    private Member getMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        return memberOptional.get();
    }

    private Role getRole(MemberDTOin dto) {
        Optional<Role> roleOptional = roleRepository.findById(dto.getRole_id());
        if (roleOptional.isEmpty()) {
            throw new BadRequestException(Error.ROLE_NOT_FOUND);
        }
        Role role = roleOptional.get();
        role.setId(dto.getRole_id());
        return role;
    }
}