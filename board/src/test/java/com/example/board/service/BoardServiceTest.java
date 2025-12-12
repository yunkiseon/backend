package com.example.board.service;

import java.beans.Transient;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.service.BoardService;



@SpringBootTest
public class BoardServiceTest {
    
    @Autowired
    private BoardService boardService;

    @Transactional(readOnly = true)
    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
        .page(1)
        .size(10)
        .build();

       PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);

        List<BoardDTO> boards = result.getDtoList();

        boards.forEach(board->{
            System.out.println(board);
        });

    
    }
}
