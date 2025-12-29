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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    
    @GetMapping("/create")
    public void getCreate() {
        log.info("영화 추가 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate (MovieDTO movieDTO, RedirectAttributes rttr) {
        
        log.info("영화 추가 요청", movieDTO);
        String title = movieServiece.register(movieDTO);
        rttr.addFlashAttribute("mno", title + "영화등록 완료");
        return "redirect:/movie/list";
    }

    @GetMapping({"/read", "/modify"})
    public void getRead(@RequestParam Long mno, Model model) {
        log.info("get or modify {}", mno);
        MovieDTO movieDTO = movieServiece.getRow(mno);
        model.addAttribute("dto", movieDTO);
    }
    
    
    
}
