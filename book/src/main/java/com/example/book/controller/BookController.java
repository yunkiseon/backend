package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/book")
@Log4j2
@Controller
public class BookController {

    @GetMapping("/register")
    public void getRegister() {
        log.info("등록화면 보여주기");
    }
    
    
}
