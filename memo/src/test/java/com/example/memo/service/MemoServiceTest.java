package com.example.memo.service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.memo.dto.MemoDTO;

@Disabled // build 시점에 실행하지 말기
@SpringBootTest
public class MemoServiceTest {
    @Autowired
    private MemoService memoService;
    @Test
    public void readAllTest(){
        List<MemoDTO> result = memoService.readAll();
        for (MemoDTO memoDTO : result) {
            System.out.println(memoDTO);
        }
    }
}
