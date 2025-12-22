package com.example.board.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.board.member.dto.MemberDTO;
import com.example.board.member.entity.Member;
import com.example.board.member.entity.entity.MemberRole;
import com.example.board.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class MemberOauth2Service extends DefaultOAuth2UserService {

    // 소셜로그인 정보로 회원 가입 처리
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest {}", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // log.info("=================");
        // oAuth2User.getAttributes().forEach((k,v) ->{
        // log.info(k + " : " + v);
        // }); 이메일, 픽처, 클라이언트 네임 등 추출되었다.

        // 1. google 이 넘겨준 정보에서 이메일 추출
        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }
        // 2. 테이블에 동일한 이메일이 있는지 확인하고 없는 경우 회원 가입/있으면 멤버 돌려받기
        Member member = saveSocialMember(email);

        // 3. MemberDTO 로 변경후 리턴
        MemberDTO dto = new MemberDTO(member.getEmail(),
                member.getPassword(), member.isFromSocial(),
                member.getRoles()
                        .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()),
                oAuth2User.getAttributes());
        dto.setName(member.getName());

        return dto;
    }

    private Member saveSocialMember(String email) {
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.USER);

        memberRepository.save(member);
        return member;

    }
}
