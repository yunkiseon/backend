package com.example.movietalk.movie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    // 특정 영화에 달려 있는 모든 영화 리뷰 가져오기 /review/300(mno)/all
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{mno}/all")
    public List<ReviewDTO> getReviews(@PathVariable Long mno) {
        log.info("특정 영화 전체 리뷰 요청 {}", mno);
        List<ReviewDTO> list = reviewService.getList(mno);
        return list;
    }
    
    // 특정 영화 리뷰 수정
    // 1) 영화 가져오기 /reviews/rno + get
    @GetMapping("/{mno}/{rno}")
    public ReviewDTO getReview(@PathVariable Long rno) {
        log.info("특정 영화의 특정 리뷰 요청 {}", rno);
        return reviewService.getRow(rno);
    }
    
    // 2) 수정 /reviews/rno + put
    // 로그인 사용자 == 리뷰사용자
    @PreAuthorize("authentication.name == #dto.email")
    @PutMapping("{mno}/{rno}")
    public ResponseEntity<Long> putReview(@PathVariable Long rno, @RequestBody ReviewDTO dto) {
        log.info("특정 영화 특정 리뷰 수정 {}", dto);
        rno = reviewService.updateRow(dto);

        // 상태코드 전송해도된다. httpstatus.ok 등
        
        return new ResponseEntity<Long>(rno,HttpStatusCode.valueOf(200));
    }
    // 3) 삭제 /reviews/rno + delete
    @PreAuthorize("authentication.name == #email")
    @DeleteMapping("{mno}/{rno}")
    public ResponseEntity<String> deleteReview(@PathVariable Long rno, String email){
        log.info("특정 영화 특정 리뷰 삭제",rno);
        reviewService.deleteRow(rno);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    // 4) 리뷰 추가 /reviews/rno + post
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{mno}")
    public Long postReview(@PathVariable Long mno, @RequestBody ReviewDTO dto) {
        log.info("특정 영화 특정 리뷰 추가",dto);
        Long rno = reviewService.insertRow(dto);
        return rno;
    }
    

}
