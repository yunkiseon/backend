package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Board;

public interface BoardRespository extends JpaRepository<Board,Long>{

    
} 
