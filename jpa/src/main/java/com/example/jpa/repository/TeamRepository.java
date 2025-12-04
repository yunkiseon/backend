package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    
} 
