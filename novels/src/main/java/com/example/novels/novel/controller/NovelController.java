package com.example.novels.novel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novels.novel.dto.NovelDTO;
import com.example.novels.novel.dto.PageRequestDTO;
import com.example.novels.novel.dto.PageResultDTO;
import com.example.novels.novel.service.NovalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RequestMapping("/api/novels")
@RequiredArgsConstructor
@Log4j2
@RestController
public class NovelController {

    private final NovalService novalService;

    // 상세조회
    @GetMapping("/{id}")
    public NovelDTO getRow(@PathVariable Long id) {
        log.info("novel 요청 {}", id);
        NovelDTO dto = novalService.getRow(id);
        return dto;
    }
    
    @GetMapping("")
    public PageResultDTO<NovelDTO> getRows(PageRequestDTO dto) {
        log.info("novel 리스트 요청 {}", dto);
        PageResultDTO<NovelDTO> result = novalService.getList(dto);
        return result;
    }
    
    @PostMapping("/add")
    public Long postRow(@RequestBody NovelDTO dto) {
        log.info("novel 추가 요청 {}", dto);
        return novalService.create(dto);
    }
    @PutMapping("/available/{id}")
    public Long putRow(@PathVariable Long id, @RequestBody NovelDTO dto) {
        log.info("novel 수정 요청 {} {}", id, dto);
        dto.setId(id);
        return novalService.updateAvailable(dto);
    }

    @PutMapping("/edit/{id}")
    public Long putNovel(@PathVariable Long id, @RequestBody NovelDTO dto) {
        log.info("novel 수정 요청 {} {}", id, dto);
        dto.setId(id);
        return novalService.update(dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        log.info("novel 삭제 요청 {}", id);
        novalService.delete(id);
        return "success";
    }
    
    
    
    
}
