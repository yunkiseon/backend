package com.example.movietalk.movie.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
// mno, 제목, 리뷰수, 평점, 등록일, img까지 들어가야 함    

private Long mno;

private String title;

@Builder.Default
private List<MovieImageDTO> movieImages = new ArrayList<>();

private double avg;

private Long reviewCnt;

private LocalDateTime createDate;
private LocalDateTime updateDate;

}
