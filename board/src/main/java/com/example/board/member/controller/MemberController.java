package com.example.board.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.member.dto.RegisterDTO;
import com.example.board.member.service.MemberService;

import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
@Controller
public class MemberController {

    
    private final MemberService clubService;

    
    // http://localhost:8080 : 모두에게 개방
    // http://localhost:8080/guest : 모두에게 개방
    // http://localhost:8080/member : 멤버에게만 개방
    // http://localhost:8080/admin : admin만 개방

    @GetMapping("/profile")
    public void getProfile() {
        log.info("profile 요청");
    }
    
    @GetMapping("/login")
    public void getLogin() {
        log.info("login form 요청");
    }
    
    @ResponseBody//templete이 아니라 객체다
    @GetMapping("/auth")
    public Authentication getAuth() {
        log.info("auth 요청");
        SecurityContext context = SecurityContextHolder.getContext();//현재 로그인한 사람의 정보
        Authentication authentication = context.getAuthentication(); // 인증된 정보들을 authentication에 담아서 아래에서 리턴
        return authentication;
    }
    
    @GetMapping("/register")
    public void getMethodName(RegisterDTO registerDTO) {
        log.info("회원가입 폼 요청");
    }
    

    @PostMapping("/register")
    public String postRegister(@Valid RegisterDTO registerDTO, BindingResult result, RedirectAttributes rttr) {
        log.info("회원가입 요청 {} ", registerDTO);
        
        if (result.hasErrors()) {
            return "/member/register";
        }
        
        //service 작업

        try {
            clubService.register(registerDTO);
        } catch (Exception e) {
            rttr.addFlashAttribute("dupEmail", e.getMessage());
            return "redirect:/member/register";
        }

        clubService.register(registerDTO);
        return "redirect:/member/login";
        
    }
    
    

    
}
