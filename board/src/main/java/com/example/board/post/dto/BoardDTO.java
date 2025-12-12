package com.example.board.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    
    private Long bno;

    private String title;

    private String content;

    // boardDTO를 화면에 보여줄 정보들만 모아서 보여주기.
    private String writerEmail; // 작성자 이메일
    private String writerName;

    private int replyCnt;
    private LocalDateTime createDateTime2;  
    private LocalDateTime updateDateTime;

}
