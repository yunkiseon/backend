package com.example.movietalk.movie.entitiy;

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
@ToString(exclude = "movie")
@Entity
public class MovieImage {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long inum;

    private String uuid;

    private String path;

    private String imgName;

    private int ord; // 이미지 순서

    @JoinColumn(name = "mno")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Movie movie;
}
