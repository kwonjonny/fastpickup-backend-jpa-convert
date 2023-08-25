package com.project.fastpickup.admin.member.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberConvertDTO {
    // tbl_member
    @NotBlank(message = "Email Can Not Be Blank")
    private String email;

    @NotBlank(message = "Member Password Can Not Be Blank")
    private String memberPw;

    @NotBlank(message = "Member Name Can Not Be Blank")
    private String memberName;

    @NotBlank(message = "Member Phone Can Not Be Blank")
    private String memberPhone;

    @NotNull(message = "Join Date Should Can Be Null")
    private Timestamp joinDate;

    private String store;
    private Long sno;
}
