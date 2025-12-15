package com.example.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RequestMapping("/memo2")
@RestController// 데이터'만' 주고 보내겠다는 의미
@Log4j2
public class BasicController {
    // 자바 객체 <==> json
    private final MemoService memoService;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }

    // -parameter flag -> pathvariable 뒤에 id 넣어주기
    @GetMapping("/sample1/{id}")
    public MemoDTO getRead(@PathVariable("id") Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }
    @GetMapping("/list")
    public List<MemoDTO> getList(Model model) {
        log.info("전체메모요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }
    
    
    
    
}
