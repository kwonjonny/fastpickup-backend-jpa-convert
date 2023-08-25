package com.project.fastpickup.admin.member.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private MemberEntity member;

    @Column(name = "rolename")
    private String rolename;

    // 정적 팩토리 메소드를 지향하라
    // CreateMemberRole. Factory Method
    public static MemberRoleEntity createMemberRole(MemberEntity memberEntity, String rolename) {
        return createOrUpdateMemberRole(memberEntity, rolename);
    }

    // 정적 팩토리 메소드를 지향하라
    // UpdateMemberRole Factory Method
    public static MemberRoleEntity updateMemberRole(MemberEntity memberEntity, String rolename) {
        return createOrUpdateMemberRole(memberEntity, rolename);
    }

    // 중복 로직을 담당하는 private 메소드
    private static MemberRoleEntity createOrUpdateMemberRole(MemberEntity memberEntity, String rolename) {
        return MemberRoleEntity.builder()
                .member(memberEntity)
                .rolename(rolename)
                .build();
    }
}