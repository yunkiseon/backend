package com.example.board.reply.dto;

import java.time.LocalDateTime;

import groovy.transform.ToString;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class ReplyDTO {
    
    private Long rno;

    private String text;

    private String replyerEmail;
    private String replyerName;

    private Long bno;

    private LocalDateTime createDateTime2;  
    private LocalDateTime updateDateTime;
}
