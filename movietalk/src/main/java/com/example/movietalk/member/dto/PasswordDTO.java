package com.example.movietalk.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordDTO {

    
    private String email;

    private String currentPassword;

    private String newPassword;
}
