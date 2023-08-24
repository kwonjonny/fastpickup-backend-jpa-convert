package com.project.fastpickup.admin.member.entity;

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
    private String email;

    @Column(length = 50, nullable = false)
    private String rolename;

    public static MemberRoleEntity createMemberRole(String email, String rolename) {
        MemberRoleEntity memberRoleEntity = MemberRoleEntity.builder()
                .email(email)
                .rolename(rolename)
                .build();
        return memberRoleEntity;
    }

    public static MemberRoleEntity updateMemberRole(String email, String rolename) {
        MemberRoleEntity memberRoleEntity = MemberRoleEntity.builder()
                .email(email)
                .rolename(rolename)
                .build();
        return memberRoleEntity;
    }
}
