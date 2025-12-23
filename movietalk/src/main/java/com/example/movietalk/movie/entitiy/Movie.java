package com.example.movietalk.movie.entitiy;

import com.example.movietalk.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Movie extends BaseEntity{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long mno;

    @Column(nullable = false)
    private String title;


}
