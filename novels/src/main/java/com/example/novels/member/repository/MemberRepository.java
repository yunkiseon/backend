package com.example.novels.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novels.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    
}
