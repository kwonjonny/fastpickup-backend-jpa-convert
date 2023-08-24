package com.project.fastpickup.admin.member.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDTO {
    private String email;
    private String memberPw;
    private String memberName;
    private String store;
    private Timestamp joinDate;
}
