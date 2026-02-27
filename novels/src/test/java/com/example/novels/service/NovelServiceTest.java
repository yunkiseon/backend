package com.example.novels.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.novels.novel.dto.NovelDTO;
import com.example.novels.novel.service.NovalService;

@Disabled
@SpringBootTest
public class NovelServiceTest {
    
    @Autowired
    private NovalService novalService;

    @Test
    public void aiGenerateTest(){
        NovelDTO dto = NovelDTO.builder()
        .title("나의 완벽한 장례식")
        .author("조현선")
        .available(false)
        .publishedDate(LocalDate.of(2026, 01, 19))
        .summary( """
            사람들은 죽는 순간, 딱 한 가지만 기억해
            방심한 순간 울컥, 심장에 꽂히는 먹먹한 여운
            삶의 마지막 순간, 당신은 무엇을 되돌리고 싶나요?
            종합병원 장례식장 너머의 조그만 매점에
            매일 밤마다 수상한 손님들이 찾아온다!""")
        .gid(7L)
        .build();
        
        Long id = novalService.create(dto);
        novalService.generatedDescription(id);
    }

}
