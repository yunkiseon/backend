package com.example.web.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Log4j2
@Controller
public class BoardController {
    @GetMapping("/board/list")
    public void getList() {
        log.info("/board/list 요청");
    }
    
}
