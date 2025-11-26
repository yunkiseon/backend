package com.example.web.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.member.dto.LoginDTO;
import com.example.web.member.dto.RegisterDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Log4j2
@Controller
public class MemberController {
    @GetMapping("/member/login")
    public void getLogin() {
        log.info("/member/login 요청");
    }
    
    @PostMapping("/member/login")
    public String postLogin(LoginDTO dto, HttpSession session) {
        log.info("로그인 post {}", dto);
        // 세션에 정보들을 기억시켜놓아서 후일, 출력하거나 창 위에 표시하여 둘 수 있다.
        session.setAttribute("loginDto", dto);
        // 이후 첫 페이지로 돌아가기 -> 리다이렉트
         return "redirect:/";
    }
    @GetMapping("/member/register")
    public void getRegister(RegisterDTO dto) {
        log.info("/member/register 요청");
    }
    @PostMapping("/member/register")
    public String postRegister(@Valid RegisterDTO dto, BindingResult result) {
        log.info("회원가입 요청 {}",dto);
        
        if (result.hasErrors()) {
            return"member/register";
        }
        return "redirect:/member/login";
    }
    
    
    
}
