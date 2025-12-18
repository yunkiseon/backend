package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/sample")
@Log4j2
@Controller
public class SampleController {
    
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
    
    

    
}
