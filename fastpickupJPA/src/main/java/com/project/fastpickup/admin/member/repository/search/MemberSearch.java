package com.project.fastpickup.admin.member.repository.search;

import com.project.fastpickup.admin.member.dto.MemberListDTO;
import com.project.fastpickup.admin.util.PageRequestDTO;
import com.project.fastpickup.admin.util.PageResponseDTO;

// Member Search Interface 
public interface MemberSearch {

    // Member List And Search Member
    PageResponseDTO<MemberListDTO> memberList(PageRequestDTO pageRequestDTO);

}
