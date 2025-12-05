package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.SportsMember;

public interface SportsMemberRepository extends JpaRepository<SportsMember, Long>{
    
}
