package com.example.movietalk.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movietalk.member.dto.AuthUserDTO;
import com.example.movietalk.member.dto.CustomUserDTO;
import com.example.movietalk.member.dto.PasswordDTO;
import com.example.movietalk.member.entitiy.Member;
import com.example.movietalk.member.repository.MemberRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;
    


    //회원탈퇴

    public void leave(CustomUserDTO dto){
        Member member = memberRepository.findByEmail(dto.getEmail())
        .orElseThrow(()->new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));
        
        if (passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            reviewRepository.deleteByMember(member);
            memberRepository.delete(member);
        } else {
            throw new IllegalStateException("현재 비밀번호 다릅니다");
        }

    }




    // 비밀번호 변경
    public void changePassword(PasswordDTO dto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(dto.getEmail())
        .orElseThrow(()->new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));

        // 현재 비밀번호 맞게 입력했는 지를 확인하기
        // dto.getCurrentPassword().equals(member.getPassword()); 는 passwordEncode 때문에 안됨
        
        if (passwordEncoder.matches(dto.getCurrentPassword(), member.getPassword())) {
            member.changePassword(passwordEncoder.encode(dto.getNewPassword()));
        } else {
            throw new IllegalStateException("현재 비밀번호 다릅니다");
        }


    }

    // 닉네임 변경
    public void changeNickname(CustomUserDTO dto){
        //대상찾기 -> 수정 내용 처리
        Member member = memberRepository.findByEmail(dto.getEmail())
        .orElseThrow(()->new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));
        member.changeNickname(dto.getNickname());

    }





    // 회원가입
    public Long join(CustomUserDTO dto){
        
        Member member = Member.builder()
        .email(dto.getEmail())
        .nickname(dto.getNickname())
        .role(dto.getRole())
        .password(passwordEncoder.encode(dto.getPassword()))
        .build();
        return memberRepository.save(member).getMid();
    }








    // 로그인 작업
    // 이걸 안하면 원래는 아이디/비밀번호 form 에서 받고 컨트롤러 -> 서비스 -> 아이디 비밀번호 일치하는 회원의 존재여부 확인
    // 정보를 세션에 담아페이지 전체 사이트에서 정보를 유지시킴.
    // 로그아웃을 하면 세션에 있는 정보를 제거하면 된다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("로그인 요청 {}", username);

        Member member = memberRepository.findByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("회원정보를 찾을 수 없습니다."));
        // return User.builder()
        // .username(member.getEmail())
        // .password(member.getPassword())
        // .build(); authUserDTO는 user 상속받아서 아래로도 가능

        CustomUserDTO customUserDTO = CustomUserDTO.builder()
        .mid(member.getMid())
        .email(member.getEmail())
        .password(member.getPassword())
        .nickname(member.getNickname())
        .role(member.getRole())
        .build();

        
        

        AuthUserDTO authUserDTO = new AuthUserDTO(customUserDTO);
        return authUserDTO;
        // 시큐리티 컨테스트로 넘어간다.
    }
    
    
}
