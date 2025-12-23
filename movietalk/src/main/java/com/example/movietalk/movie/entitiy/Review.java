package com.example.movietalk.movie.entitiy;

import com.example.movietalk.common.BaseEntity;
import com.example.movietalk.member.entitiy.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie","member"})
@Entity
public class Review extends BaseEntity{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long rno;

    private int grade; // 리뷰 점수

    private String text; // 리뷰

    // 하나의 영화에 여러 개의 리뷰

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Movie movie;    

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Member member;
}
