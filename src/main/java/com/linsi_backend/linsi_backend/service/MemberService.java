package com.linsi_backend.linsi_backend.service;


import com.linsi_backend.linsi_backend.service.dto.request.MemberDTOin;
import com.linsi_backend.linsi_backend.service.dto.request.MemberFilterDTO;
import com.linsi_backend.linsi_backend.service.dto.response.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberDTO create(MemberDTOin dto);

    MemberDTO getById(Long id);

    Page<MemberDTO> getAll(MemberFilterDTO filter, Pageable pageable);

    MemberDTO update(Long id, MemberDTOin dto);

    void delete(Long id);
}
