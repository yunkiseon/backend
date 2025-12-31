package com.example.movietalk.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
@Controller
public class MemberController {
    // login + GET
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }
    
}
