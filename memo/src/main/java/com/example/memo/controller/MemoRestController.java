package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/memo")
public class MemoRestController {
    private final MemoService memoService;

    // postman에 memo/2 보내기
    @GetMapping("/{id}")
    public MemoDTO getRead(@PathVariable("id") Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }
    // responseEntity : 데이터 + 상태코드(200, 400, 500)
    @GetMapping("/list2")
    public List<MemoDTO> getRead2() {
        List<MemoDTO> list = memoService.readAll();
        return list;
    }
}
