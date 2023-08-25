package com.project.fastpickup.admin.member.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.fastpickup.admin.member.dto.MemberConvertDTO;
import com.project.fastpickup.admin.member.dto.MemberDTO;
import com.project.fastpickup.admin.member.dto.MemberReadDTO;
import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.entity.MemberRoleEntity;
import com.project.fastpickup.admin.member.exception.UserNotFoundException;
import com.project.fastpickup.admin.member.repository.MemberRepository;
import com.project.fastpickup.admin.member.repository.MemberRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// Kakao Login User Details Class 
@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // 의존성 주입
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder;

    // Oauth2User LoadByUserRequest
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Is Running Load By User Request");
        log.info(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }
        log.info("Email: " + email);

        // DB에 해당 사용자가있으면
        Optional<MemberEntity> memberEntity = memberRepository.findByEmailWithRoles(email);

        // MemberEntity를 MemberReadTO로 변환
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmailWithRoles(email);

        MemberReadDTO memberReadDTO = null;
        if (optionalMemberEntity.isPresent()) {
            memberReadDTO = convertToMemberReadDTO(optionalMemberEntity.get());
        }

        if (memberReadDTO == null) {

            MemberConvertDTO socialMember = MemberConvertDTO.builder()
                    .email(email)
                    .memberName("카카오 사용자")
                    .memberPw(passwordEncoder.encode("1111"))
                    .memberPhone("010-1111-1111")
                    .build();
            String memberRole = "USER";

            MemberEntity newMemberEntity = convertToMemberEntity(socialMember);
            newMemberEntity = memberRepository.save(newMemberEntity); 

            MemberRoleEntity memberRoleEntity = MemberRoleEntity.createMemberRole(newMemberEntity, memberRole);
            memberRoleRepository.save(memberRoleEntity);

            // PW를 사용하지않는 자원 이미 카카오에서 인증이되었기 때문에 비워둔다.
            MemberDTO memberDTO = new MemberDTO(email, "", "카카오사용자", List.of("USER"));
            return memberDTO;
        } else {
            MemberDTO memberDTO = new MemberDTO(
                    memberReadDTO.getEmail(),
                    memberReadDTO.getMemberPw(),
                    memberReadDTO.getMemberName(),
                    memberReadDTO.getRolenames());
            return memberDTO;
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO Login Is Running");
        Object value = paramMap.get("kakao_account");
        log.info("value: " + value);
        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");
        log.info("email: " + email);
        return email;
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

    private MemberEntity convertToMemberEntity(MemberConvertDTO memberConvertDTO) {
        return MemberEntity.builder()
                .email(memberConvertDTO.getEmail())
                .memberName(memberConvertDTO.getMemberName())
                .memberPw(memberConvertDTO.getMemberPw())
                .memberPhone(memberConvertDTO.getMemberPhone())
                .build();

    }
}