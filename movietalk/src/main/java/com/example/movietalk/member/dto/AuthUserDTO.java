package com.example.movietalk.member.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuthUserDTO extends User{

    private CustomUserDTO customUserDTO;

    // Collecton : List, Set
    // List list = new ArrayList<>(); 혹은 List.of(new CustomUserDTo(), new CustomUserDTO()) 의 형태
    // board 프로젝트와의 차이. board에서 MemberDTO에 User를 extends 하고, 화면상 필요한 정보도 관리했다.
    // 그러나 이번에는 화면상 필요한 정보는 customuserdto로 객체로 만들었고 user 가 넘겨주는 정보는 직접 관리.


    public AuthUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUserDTO(CustomUserDTO customUserDTO){
        super(customUserDTO.getEmail(),customUserDTO.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+customUserDTO.getRole())));
        this.customUserDTO = customUserDTO;
    }
    
}
