package com.example.novels.member.service;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.novels.member.dto.MemberDTO;
import com.example.novels.member.dto.RegisterDTO;
import com.example.novels.member.entity.Member;
import com.example.novels.member.entity.constant.MemberRole;
import com.example.novels.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@Setter
@Service
@RequiredArgsConstructor
public class NovelMemberService implements UserDetailsService{

   private final PasswordEncoder passwordEncoder;
   private final MemberRepository memberRepository;



   public void register(RegisterDTO registerDTO) throws IllegalStateException{

      //중복 이메일 확인
      Optional<Member> result = memberRepository.findById(registerDTO.getEmail());
      if (result.isPresent()) {
         throw new IllegalStateException("이미 가입된 회원입니다.");
      }
      
            Member member = Member.builder()
            .email(registerDTO.getEmail())
            .nickname(registerDTO.getNickname())
            .fromSocial(false)
            .pw(passwordEncoder.encode(registerDTO.getPassword()))
            .build();
            member.addRole(MemberRole.MEMBER);
            memberRepository.save(member);
        
   }











    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // 임시계정을 만들 때 사용했던 userdetails를 호출한 것 이것으로 로그인 시, username 등의 정보를 가져올 수 있다.
       log.info("clubservice username {} ", username);

       // login email 찾아와서 
       // memberRepository.findById(username); -> id 가 email 이여서 가능은 하나,
       // social 정보도 알고싶다면
       Member member = memberRepository.findByEmailAndFromSocial(username, false)
       .orElseThrow(() -> new UsernameNotFoundException("이메일 확인"));
    //Optional 뗴는 방법으로 위의 elseThrow를 쓰던가  member.get(); 을 하는 것이다.
    // 만약 new UsernameNotFo~~ 없이 하면 nosuchElementException이 뜬다.
    // 이제 member -> memberDTO해야한다.
    // member.roll 은 여러 개가 출력되야 할 때가 있기 때문

      
       MemberDTO dto = new MemberDTO(member.getEmail(), member.getPw(),member.getNickname(), member.isFromSocial(),member.getRoles()
      .stream().map(role -> role.name()).collect(Collectors.toList()));

       dto.setNickname(member.getNickname());
       
       return dto;
    }

    
    
}
