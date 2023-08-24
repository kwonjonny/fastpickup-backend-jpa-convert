package com.project.fastpickup.member.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.fastpickup.admin.member.entity.MemberEntity;
import com.project.fastpickup.admin.member.entity.MemberRoleEntity;
import com.project.fastpickup.admin.member.repository.MemberRepository;
import com.project.fastpickup.admin.member.repository.MemberRoleRepository;

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
}
