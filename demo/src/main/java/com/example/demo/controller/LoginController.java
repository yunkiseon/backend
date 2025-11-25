package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.LoginDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Log4j2
@Controller
@RequestMapping("/member")

public class LoginController {
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }
    // @PostMapping("/login")
    // public void postLogin(String id, String password) {
    //     log.info("login post {}, {}", id, password);
    // }
    @PostMapping("/login")
    public void postLogin(@ModelAttribute("login") LoginDTO login) {
        log.info("login post");
        log.info("{}", login);
    }
    
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
