package com.project.fastpickup.member.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.fastpickup.admin.member.dto.MemberListDTO;
import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.entity.MemberRoleEntity;
import com.project.fastpickup.admin.member.repository.MemberRepository;
import com.project.fastpickup.admin.member.repository.MemberRoleRepository;
import com.project.fastpickup.admin.util.PageRequestDTO;
import com.project.fastpickup.admin.util.PageResponseDTO;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

// Member Repository Test class
@Log4j2
@SpringBootTest
public class MemberRepositoryTest {

    // 의존성 주입
    @Autowired(required = false)
    private MemberRepository memberRepository;

    @Autowired(required = false)
    private MemberRoleRepository memberRoleRepository;

    // BeforEach 사용을 위한 SetUp Test 시작시 메모리 선 참조
    private static final String TEST_EMAIL = "thistrik@naver.com";
    private static final String TEST_MEMBER_PW = "1111";
    private static final String TEST_MEMBER_NAME = "권성준";
    private static final String TEST_MEMBER_PHONE = "010-3099-0648";
    private static final String TEST_STORE_ROLE = "m";
    private static final String TEST_ROLE = "USER";

    private MemberEntity memberEntity;
    private MemberEntity memberUpdateEntity;

    private MemberRoleEntity memberRoleEntity;
    private MemberRoleEntity memberUpdateRoleEntity;

    // Set Up
    @BeforeEach
    public void setUp() {
        memberEntity = MemberEntity.createMember(TEST_EMAIL, TEST_MEMBER_PW, TEST_MEMBER_NAME, TEST_MEMBER_PHONE,
                TEST_STORE_ROLE, null);

        memberUpdateEntity = MemberEntity.updateMember(TEST_EMAIL, TEST_MEMBER_PW, TEST_MEMBER_NAME, TEST_MEMBER_PHONE,
                TEST_STORE_ROLE, null);

        memberRoleEntity = MemberRoleEntity.createMemberRole(TEST_EMAIL, TEST_ROLE);

        memberUpdateRoleEntity = MemberRoleEntity.updateMemberRole(TEST_EMAIL, TEST_EMAIL);
    }

    // Create Member Repository Test
    @Test
    @Transactional
    @DisplayName("Repository: 회원 생성 테스트")
    public void createMemberTest() {
        // GIVEN
        log.info("=== Start Create Member Test ===");
        // WHEN
        MemberEntity saveMember = memberRepository.save(memberEntity);
        MemberRoleEntity saveMemberRole = memberRoleRepository.save(memberRoleEntity);
        // THEN
        Assertions.assertNotNull(saveMember, "saveMember Should Be Not Null ");
        Assertions.assertNotNull(saveMemberRole, "saveMemberRole Should Be Not Null");
        Assertions.assertEquals(TEST_EMAIL, saveMember.getEmail());
        Assertions.assertEquals(TEST_MEMBER_NAME, saveMember.getMemberName());
        Assertions.assertEquals(TEST_MEMBER_PW, saveMember.getMemberPw());
        Assertions.assertEquals(TEST_MEMBER_PHONE, saveMember.getMemberPhone());
        Assertions.assertEquals(TEST_STORE_ROLE, saveMember.getStore());
        log.info("=== End Create Member Test ===");
    }

    // Read Member Repository Test
    @Test
    @Transactional
    @DisplayName("Repository: 회원 조회 테스트")
    public void readMemberTest() {
        // GIVEN
        log.info("=== Start Read Member Test ===");
        // WHEN
        Optional<MemberEntity> readMemberOptional = memberRepository.findById(TEST_EMAIL);
        // THEN
        Assertions.assertTrue(readMemberOptional.isPresent(), "Member should be present");
        MemberEntity readMember = readMemberOptional.get();
        log.info("회원 이메일:" + readMember.getEmail());
        Assertions.assertEquals(TEST_EMAIL, readMember.getEmail(), "Emails should match");
        Assertions.assertEquals(TEST_MEMBER_NAME, readMember.getMemberName(), "Names should match");
        log.info("=== End Read Member Test ===");
    }

    // Delete Member Repository Test
    @Test
    @Transactional
    @DisplayName("Repository: 회원 탈퇴 테스트")
    public void deleteMemberTest() {
        // GIVEN
        log.info("=== Start Delete Member Test ===");
        // WHEN
        memberRepository.deleteById(TEST_EMAIL);
        memberRoleRepository.deleteById(TEST_EMAIL);
        // THEN
        Optional<MemberEntity> deletedMember = memberRepository.findById(TEST_EMAIL);
        Optional<MemberRoleEntity> deleteMemberRole = memberRoleRepository.findById(TEST_EMAIL);
        Assertions.assertFalse(deletedMember.isPresent(), "deletedMember Should Be Present");
        Assertions.assertFalse(deleteMemberRole.isPresent(), "deletedMemberRole Should Be Present");
        log.info("=== End Delete Member Test ===");
    }

    // Update Member Repository Test
    @Test
    @Transactional
    @DisplayName("Repository: 회원 업데이트 테스트")
    public void updateMemberTest() {
        // GIVEN
        log.info("=== Start Update Member Test ===");
        // WHEN
        MemberEntity updateMember = memberRepository.save(memberUpdateEntity);
        MemberRoleEntity updateMemberRole = memberRoleRepository.save(memberUpdateRoleEntity);
        // THEN
        Optional<MemberEntity> updatedMember = memberRepository.findById(TEST_EMAIL);
        Optional<MemberRoleEntity> updatedMemberRole = memberRoleRepository.findById(TEST_EMAIL);
        Assertions.assertEquals(TEST_EMAIL, updateMember.getEmail());
        Assertions.assertEquals(TEST_EMAIL, updateMemberRole.getEmail());
        Assertions.assertTrue(updatedMember.isPresent(), "Updated member should be present.");
        Assertions.assertTrue(updatedMemberRole.isPresent(), "Updated member role should be present.");
    }

    // List Member Repository Test 
    @Test
    @Transactional
    @DisplayName("Repository: 회원 리스트 테스트")
    public void listMemberTest() {
        // GIVEN 
        log.info("=== Start List Member Test ===");
        // WHEN 
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<MemberListDTO> listMember = memberRepository.memberList(pageRequestDTO);
        // THEN 
        log.info("회원 리스트: "+ listMember.getList());
    }
}
