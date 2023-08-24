package com.project.fastpickup.admin.member.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_member_role")
public class MemberRoleEntity {

    // tbl_member_role
    @Id
    @Column(length = 100)
    @Comment("회원 이메일")
    private String email;

    @Comment("회원 권한 여부")
    @Column(length = 50, nullable = false)
    private String rolename;

    // 정적 팩토리 메소드를 지향하라
    // CreateMemberRole Factory Method
    public static MemberRoleEntity createMemberRole(String email, String rolename) {
        MemberRoleEntity memberRoleEntity = MemberRoleEntity.builder()
                .email(email)
                .rolename(rolename)
                .build();
        return memberRoleEntity;
    }

    // 정적 팩토리 메소드를 지향하라
    // UpdateMemberRole Factory Method
    public static MemberRoleEntity updateMemberRole(String email, String rolename) {
        MemberRoleEntity memberRoleEntity = MemberRoleEntity.builder()
                .email(email)
                .rolename(rolename)
                .build();
        return memberRoleEntity;
    }
}
