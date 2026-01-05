package com.example.movietalk.member.dto;

import com.example.movietalk.member.entitiy.constant.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomUserDTO {
    private Long mid;

    @NotBlank(message = "필수입력 요소")
    @Email(message = "이메일 형식을 확인해 주세요")
    private String email;

    @NotBlank(message = "필수입력 요소")
    private String password;

    @NotBlank(message = "필수입력 요소")
    private String nickname;

    private Role role;
}
