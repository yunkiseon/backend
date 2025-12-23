package com.example.movietalk.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movietalk.member.entitiy.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
