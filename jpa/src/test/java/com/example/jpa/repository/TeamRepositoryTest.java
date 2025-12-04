package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.TeamMember;

@SpringBootTest
public class TeamRepositoryTest {
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest(){
        TeamMember member = TeamMember.builder().name("홍길동").build();
        teamMemberRepository.save(member);
    }
    
}
