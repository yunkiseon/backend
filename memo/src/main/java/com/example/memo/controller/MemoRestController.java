package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



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
    // localost:8080/memo +get
    @GetMapping("")
    public List<MemoDTO> getList() {
        List<MemoDTO> list = memoService.readAll();
        return list;
    }
    // localost:8080/memo +post
    @PostMapping("")
    public ResponseEntity<Long> postCreate(@RequestBody MemoDTO memoDTO) {
        // 데이터를 주고 받을 때 json으로 주고 받아야하는데 
        // 대게 form의 형태이기에, 그것을 json의 형태로 바꾸어줘야한다.
        // 그리고 requestBody는 json으로 들어온 데이터를 java 객체 매핑해준다.
        // ResponseBody와 RequestBody 차이. 전자는 일반컨트롤러에서 템플릿을 찾지말고
        // 데이터만 보낼 것이라는 의미
        log.info("삽입 {}", memoDTO);
        Long id = memoService.insert(memoDTO);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    // put도 delete도 rest에서만 가능한 개념. 당연히 form에서 method로 사용불가

    @PutMapping("")
    public ResponseEntity<Long> put(@RequestBody MemoDTO memoDTO) {
        log.info("수정 {}", memoDTO);
        Long id = memoService.modify(memoDTO);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        log.info("삭제 {}", id);
        memoService.remove(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
}
