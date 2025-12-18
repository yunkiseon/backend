package com.example.club.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
@Controller
public class MemberController {
    
    // http://localhost:8080 : 모두에게 개방
    // http://localhost:8080/guest : 모두에게 개방
    // http://localhost:8080/member : 멤버에게만 개방
    // http://localhost:8080/admin : admin만 개방

    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest 요청");
    }
    @GetMapping("/member")
    public void getMember() {
        log.info("member 요청");
    }
    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 요청");
    }
    @GetMapping("/info")
    public void getInfo() {
        log.info("info 요청");
    }
    @GetMapping("/login")
    public void getLogin() {
        log.info("login form 요청");
    }
    
    @GetMapping("auth")
    public Authentication getAuth() {
        log.info("auth 요청");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
    
    

    
}
