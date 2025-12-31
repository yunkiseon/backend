package com.example.movietalk.member.dto;

import com.example.movietalk.member.entitiy.constant.Role;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
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

    private String email;

    private String password;

    private String nickname;

    private Role role;
}
