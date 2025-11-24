package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Log4j2
@Controller
public class BoardController {
    @GetMapping("/board/add")
    public void getMethodName() {
        log.info("/board/add 요청");
    }    
}
