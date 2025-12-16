package com.example.board.reply.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.reply.dto.ReplyDTO;
import com.example.board.reply.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RequiredArgsConstructor
@RequestMapping("/replies")
@Log4j2
@RestController
public class ReplyController {
    private final ReplyService replyService;

    // board 의 bno라는 의미 bno로 전체 reply 가져오기
    @GetMapping("/board/{bno}")
    public List<ReplyDTO> getList(@PathVariable Long bno) {
        log.info("{} 댓글 요청", bno);

        return replyService.getList(bno);
    }
    //rno 로 가져오기

    @GetMapping("/{rno}")
    public ReplyDTO getRow(@PathVariable Long rno) {
        log.info("{} 댓글 요청", rno);

        return replyService.getRow(rno);
    }

    @PostMapping("/new")
    public Long postReply(@RequestBody ReplyDTO dto) {
        log.info("삽입 요청 {}", dto);
        Long rno = replyService.create(dto);
        return rno;
    }
    @DeleteMapping("/{rno}")
    public String deleteReply(@PathVariable Long rno) {
        log.info("삭제 요청 {}", rno);
        replyService.delete(rno);
        return "success";
    }
    
    
    @PutMapping("/{rno}")
    public Long putReply(@RequestBody ReplyDTO dto) {
        log.info("수정 요청 {}", dto);
        
        Long rno = replyService.update(dto);

        return rno;
    }
    
    
}
