package com.example.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    
    private Long bno;

    private String title;

    private String content;


    private String email; // 작성자 이메일
}
