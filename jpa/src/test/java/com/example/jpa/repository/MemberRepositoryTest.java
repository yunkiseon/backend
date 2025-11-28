package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.constant.RoleType;

@SpringBootTest
public class MemberRepositoryTest {
    
    @Autowired
    private MemberRespository memberRespository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Member member = Member.builder()
            .userId("userId"+i)
            .name("guest")
            .role(RoleType.MEMBER)
            .build();
            memberRespository.save(member);
            
        }
    }
    //userId9 인 사람의 role 변경
    @Test
    public void updateTest(){
        Optional<Member> result = memberRespository.findById(9L);//여기서 id는 @id 를 의미.
        // result.get(); 이것이 원래 배운 방법
        result.ifPresent(member -> {//result가 만약 있다면~ 이라는 의미
            member.changeRole(RoleType.ADMIN);
            memberRespository.save(member);
        });
    }
    @Test
    public void deleteTest(){
        memberRespository.deleteById(10L);
    }
    @Test
    public void readTest(){
    Optional<Member> result  = memberRespository.findById(5l);
        result.ifPresent(member-> System.out.println(member));
    }
    @Test
    public void readTest2(){
        List<Member> members = memberRespository.findAll();
        members.forEach(member->System.out.println(member));
    }
}
