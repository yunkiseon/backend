package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@Controller
public class HomeController {
    // get 요청으로 들어올 때 get으로 받기
    @GetMapping("/home")//괄호 안 주소는 임의로 작성
    public void getHome() {
        log.info("home"); //System.out.println() 과 같다. 대신 log는 파일이나 db로 저장해놓을 수 있다. 

    }
    @GetMapping("/add")
    public String getAdd() {
        return "result";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("calc get");
    }
    

    @PostMapping("/calc")
    public void postCalc(int num1) {
        log.info("calc post {}",num1);
    }
    
}
