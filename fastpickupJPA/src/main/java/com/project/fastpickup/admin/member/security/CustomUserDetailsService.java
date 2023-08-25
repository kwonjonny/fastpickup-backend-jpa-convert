package com.project.fastpickup.admin.member.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.fastpickup.admin.member.dto.MemberDTO;
import com.project.fastpickup.admin.member.dto.MemberReadDTO;
import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.entity.MemberRoleEntity;
import com.project.fastpickup.admin.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// Custom User Details Servier Class
@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // 의존성 주입
    private final MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Is Running LoadUserByUserName");

        // Optional 처리
        MemberEntity memberEntity = memberRepository.findByEmailWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // MemberEntity를 MemberReadDTO로 변환
        MemberReadDTO readDTO = convertToMemberReadDTO(memberEntity);

        MemberDTO memberDTO = new MemberDTO(username,
                readDTO.getMemberPw(),
                readDTO.getMemberName(),
                readDTO.getRolenames());
        return memberDTO;
    }

    private MemberReadDTO convertToMemberReadDTO(MemberEntity memberEntity) {
        List<String> roleNames = memberEntity.getRoles().stream()
                .map(MemberRoleEntity::getRolename)
                .collect(Collectors.toList());

        return MemberReadDTO.builder()
                .email(memberEntity.getEmail())
                .memberPw(memberEntity.getMemberPw())
                .memberName(memberEntity.getMemberName())
                .rolenames(roleNames)
                .build();
    }
}
