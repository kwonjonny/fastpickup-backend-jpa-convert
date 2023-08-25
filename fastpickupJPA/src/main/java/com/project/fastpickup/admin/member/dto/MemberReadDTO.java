package com.project.fastpickup.admin.member.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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
public class MemberReadDTO {
    
    // tbl_member 
    @NotBlank(message = "Email Can Not Be Blank")
    private String email;

    @NotBlank(message = "Member Password Can Not Be Blank")
    private String memberPw;

    @NotBlank(message = "Member Name Can Not Be Blank")
    private String memberName;

    @NotBlank(message = "Role Names Can Not Be Blank")
    private List<String> rolenames;
}
