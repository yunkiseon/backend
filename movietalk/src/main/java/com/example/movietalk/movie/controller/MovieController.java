package com.example.movietalk.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.service.MovieServiece;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/movie")
@RequiredArgsConstructor
@Log4j2
@Controller
public class MovieController {
    
    private final MovieServiece movieServiece;

    @GetMapping("/list")
    public void getMovieList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("영화 리스트 요청 {}", pageRequestDTO);
        PageResultDTO<MovieDTO> result = movieServiece.getMovieList(pageRequestDTO);
        model.addAttribute("result", result);
    }
    
}
