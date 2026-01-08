package com.example.movietalk.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class HomeController {
    
    @GetMapping("/")
    public String getHome() {
        return "redirect:/movie/list";
    }

    @GetMapping("/access/denied")
    public void getMethodName() {
        
    }
    

    
    
    
}
