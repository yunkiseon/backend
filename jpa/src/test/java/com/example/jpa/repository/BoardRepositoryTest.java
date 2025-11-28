package com.example.jpa.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRespository boardRespository;
    //Board 10 개 삽입 
    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Board board = Board.builder()
            .title("타이틀"+i)
            .content("붕어빵 맛있음")
            .writer("홍길동")
            .build();
            boardRespository.save(board);
        }
    }
    
    //수정: title, content 
    @Test
    public void updateTest(){
        Optional<Board> result = boardRespository.findById(2l);
        result.ifPresent(board -> {
            board.changeTitle("title");
            board.changeContent("슈붕도 인정");
            boardRespository.save(board);
        });
    }

    // 조회 
    @Test
    public void readTest(){
        System.out.println(boardRespository.findById(3l).get());
    }
    @Test
    public void readTest2(){
        boardRespository.findAll().forEach(board -> System.out.println(board));
    }
    // 삭제
    @Test
    public void deleteTest(){
        boardRespository.deleteById(5l);
    }
}
