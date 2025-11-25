package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Info;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@Controller
public class HomeController {
    // get 요청으로 들어올 때 get으로 받기
    @GetMapping("/home")//괄호 안 주소는 임의로 작성
    public void getHome(int bno, String name) {
        log.info("home {}, {}", bno, name  ); //System.out.println() 과 같다. 대신 log는 파일이나 db로 저장해놓을 수 있다. 

    }
    @GetMapping("/add")
    public String getAdd(@RequestParam int num1, @RequestParam String op, @RequestParam int num2, Model model) {
        log.info("사칙연산 요청 {} {} {} ", num1, op, num2);
        int result =0;
        switch (op) {
            case "+": result = num1 + num2;
                break;
            case "-": result = num1 - num2;
                break;
            case "*": result = num1 * num2;
                break;
            case "/": result = num1 / num2;
                break;
        
            default:
                break;
        }
        log.info("result = "+ result);
        model.addAttribute("num1", num1);
        model.addAttribute("op", op);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);
        return "exam3";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("calc get");
    }
    

    @PostMapping("/calc")
    public void postCalc(@RequestParam(required = false, defaultValue = "0") int num1,@RequestParam(required = false, defaultValue = "0") int num2) {
        log.info("calc post {} {}",num1,num2);
    }
    
    @GetMapping("/info")
    public void getInfo() {
        log.info("info.html 호출");
    }

    // @PostMapping("/info")
    // public void postInfo(Info info) {
    //     log.info("info post");
    //     log.info("{}", info.toString());
    // }
    @PostMapping("/info")
    public void postInfo(HttpServletRequest request) {
        log.info("info post");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        String addr = request.getParameter("addr");
        String tel = request.getParameter("tel");
        log.info("{},{},{},{} ", username, age, addr,tel);
    }
    
    @GetMapping("/") // == http://localhost:8080
    public String getIndex(RedirectAttributes rttr) {
        // return "index";
        rttr.addAttribute("bno",10);
        rttr.addAttribute("name","홍길동");
        rttr.addFlashAttribute("money", "1000");
        return "redirect:/home";
    }
    
    

}
