package com.example.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.post.entity.Board;
import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository{

} 
