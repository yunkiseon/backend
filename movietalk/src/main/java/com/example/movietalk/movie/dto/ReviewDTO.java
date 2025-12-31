package com.example.movietalk.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long rno;

    private int grade; // 리뷰 점수

    private String text;

    //movie의 mno 필요
    private Long mno;

    // member의 mid, email, nickname만 
    private Long mid;
    private String email;
    private String nickname;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
