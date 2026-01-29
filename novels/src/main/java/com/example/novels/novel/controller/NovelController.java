package com.example.novels.novel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novels.novel.dto.NovelDTO;
import com.example.novels.novel.dto.PageRequestDTO;
import com.example.novels.novel.dto.PageResultDTO;
import com.example.novels.novel.service.NovalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Tag(name = "Response Novels", description = "Response Novel API")
@RequestMapping("/api/novels")
@RequiredArgsConstructor
@Log4j2
@RestController
public class NovelController {

    private final NovalService novalService;

    // 상세조회
    @Operation(summary = "novel 조회", description = "novel 상세 조회 API- 완료 여부 포함 가능")
    @GetMapping("/{id}")
    public NovelDTO getRow(@PathVariable Long id) {
        log.info("novel 요청 {}", id);
        NovelDTO dto = novalService.getRow(id);
        return dto;
    }
    
    @Operation(summary = "novel 전체 조회", description = "novel 전체 조회 API- 완료 여부 포함 가능")
    @GetMapping("")
    public PageResultDTO<NovelDTO> getRows(PageRequestDTO dto) {
        log.info("novel 리스트 요청 {}", dto);
        PageResultDTO<NovelDTO> result = novalService.getList(dto);
        return result;
    }
    
    @Operation(summary = "novel 추가", description = "novel 추가 API")
    @PostMapping("/add")
    public Long postRow(@RequestBody NovelDTO dto) {
        log.info("novel 추가 요청 {}", dto);
        return novalService.create(dto);
    }

    @Operation(summary = "novel 수정", description = "novel 수정 API - 이용 가능 여부")
    @PutMapping("path/{id}")
    public Long putRow(@PathVariable Long id, @RequestBody NovelDTO dto) {
        log.info("novel 수정 요청 {} {}", id, dto);
        dto.setId(id);
        return novalService.update(dto);
    }

    @Operation(summary = "novel 삭제", description = "novel 삭제 API")
    @DeleteMapping("")
    public String delete(@PathVariable Long id) {
        log.info("novel 삭제 요청 {}", id);
        novalService.delete(id);
        return "success";
    }
    
    
    
    
}
