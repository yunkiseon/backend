package com.example.board.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegisterDTO {
    //회원가입용
     @Email(message = "이메일 확인")
    @NotBlank(message = "필수입력 요소")
    private String email;

    @NotBlank(message = "필수입력 요소")
    private String password;

    @NotBlank(message = "필수입력 요소")
    private String name;

    private boolean fromSocial;
}
