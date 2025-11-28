package com.example.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

@SpringBootTest
public class MemorepositoryTest {
    
    @Autowired
    private MemoRespository memoRespository;

    @Test
    public void insertTest(){
        for (int i = 0; i < 10; i++) {
            Memo memo = Memo.builder()
            .text("memo text"+i)
            .build();
            memoRespository.save(memo);
        }
    
    }
    
    @Test
    public void updateTest(){
        Memo memo = memoRespository.findById(3L).get();
        memo.changeText("변경 text");
        memoRespository.save(memo);
        
    }

    @Test
    public void deleteTest(){
        memoRespository.deleteById(10L);
    }

    @Test
    public void readTest(){
        Memo memo = memoRespository.findById(3L).get();
        System.out.println(memo);
    }
    @Test
    public void readTest2(){

        List<Memo> memos = memoRespository.findAll();
        for (Memo memo : memos) {
            System.out.println(memo);
        }
    }
}
