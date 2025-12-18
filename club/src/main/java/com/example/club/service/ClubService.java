package com.example.club.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.club.dto.MemberDTO;
import com.example.club.entity.Member;
import com.example.club.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@Setter
@Service
@RequiredArgsConstructor
public class ClubService implements UserDetailsService{

    private final MemberRepository memberRepository;

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
       
       
       return new MemberDTO(username, username, false, null);
    }
    
}
