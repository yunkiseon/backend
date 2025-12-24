package com.example.movietalk.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.service.MovieServiece;

@SpringBootTest
public class MovieServiceTest {
    
    @Autowired
    private MovieServiece movieServiece;


    @Test
    public void getMovieListTest(){
        PageRequestDTO requestDTO = PageRequestDTO.builder()
        .page(1)
        .size(20)
        .build();
        PageResultDTO<MovieDTO> result = movieServiece.getMovieList(requestDTO);

        result.getDtoList().forEach(dto -> System.out.println(dto));
    }
}
