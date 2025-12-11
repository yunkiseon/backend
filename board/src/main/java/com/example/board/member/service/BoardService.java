package com.example.board.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Board> getList(PageRequestDTO requestDTO){
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(),Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        return result;
    }

    public void getRow(Long bno){

    }

    public void insert(BoardDTO dto){
        
    }
    public void update(BoardDTO dto){

    }
    public void delete(BoardDTO dto){

    }

}
