package com.project.fastpickup.admin.member.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tbl_member")
public class MemberEntity {

    // tbl_member Entity
    @Id
    @Column(length = 100)
    @Comment("회원 이메일")
    private String email;

    @Column(name = "memberPw", length = 200, nullable = false)
    @Comment("회원 비밀번호")
    private String memberPw;

    @Column(name = "memberName", length = 100, nullable = false)
    @Comment("회원 이름")
    private String memberName;

    @Column(name = "memberPhone", length = 20, nullable = false)
    @Comment("회원 번호")
    private String memberPhone;

    @Column(length = 100, nullable = false)
    @Comment("회원 가맹점 여부")
    private String store;

    @CreationTimestamp
    @Column(name = "joinDate")
    @Comment("회원 가입 일")
    private Timestamp joinDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRoleEntity> roles = new ArrayList<>();

    // 정적 팩토리 메소드를 지향하라.
    // CreateMember Factory Method
    public static MemberEntity createMember(String email, String memberPw, String memberName, String memberPhone,
            String store, Timestamp joinDate) {
        return createOrUpdateMember(email, memberPw, memberName, memberPhone, store, joinDate);
    }

    // 정적 팩토리 메소드를 지향하라.
    // UpdateMember Factory Method
    public static MemberEntity updateMember(String email, String memberPw, String memberName, String memberPhone,
            String store, Timestamp joinDate) {
        return createOrUpdateMember(email, memberPw, memberName, memberPhone, store, joinDate);
    }

    // 중복 로직을 담당하는 private 메소드
    private static MemberEntity createOrUpdateMember(String email, String memberPw, String memberName,
            String memberPhone,
            String store, Timestamp joinDate) {
        return MemberEntity.builder()
                .email(email)
                .memberPw(memberPw)
                .memberName(memberName)
                .store(store)
                .memberPhone(memberPhone)
                .joinDate(joinDate)
                .build();
    }
}
