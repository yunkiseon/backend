package com.example.club.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.club.entity.Member;
import com.example.club.entity.constant.ClubMemberRole;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void inserTest(){
        // 회원 10명 생성 + 권한 부여
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
            .email("user" + i + "@gmail.com")
            .name("user"+i)
            .fromSocial(false)
            .password(passwordEncoder.encode("1111"))
            .build();

            member.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                member.addMemberRole(ClubMemberRole.MANAGER);
            }
            if (i > 9) {
                member.addMemberRole(ClubMemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }
    //transactional로 하면 select 를 여러 번 해서 출력한다. 그러나 security를 사용할 땐 role을 항상 가지고 다녀야한다.
    // 그래서 repository에서 join 하는 것이 유리하다
    // @Transactional
    @Test
    public void testLogin(){

        Member member = memberRepository.findByEmailAndFromSocial("user10@gmail.com", false).get();
        System.out.println(member);
    }
    
}
