package com.example.movietalk.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movietalk.member.entitiy.Member;
import com.example.movietalk.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 로그인 작업
    // 이걸 안하면 원래는                                        아이디/비밀번호 form 에서 받고 컨트롤러 -> 서비스 -> 아이디 비밀번호 일치하는 회원의 존재여부 확인
    // 정보를 세션에 담아페이지 전체 사이트에서 정보를 유지시킴.
    // 로그아웃을 하면 세션에 있는 정보를 제거하면 된다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));
        return User.builder()
        .username(member.getEmail())
        .password(member.getPassword())
        .build();
    }
    
    
}
