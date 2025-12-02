package com.example.student.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.student.dto.StudentDTO;
import com.example.student.service.StudentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RequestMapping("/student")
@Log4j2
@Controller
public class StudentController {
    // 학생 등록 : get보여주기 : student/register post등록 : student/register

    private final StudentService studentService;

    @GetMapping("/register")
    public void getRegister() {
        log.info("등록화면 보여주기");
    }
    @PostMapping("/register")
    public String postRegister(StudentDTO dto) {
        log.info("등록 {}", dto);
        String name = studentService.insert(dto);
        return  "redirect:/student/list";
        }


    // 학생 정보 수정 : get보여주기 : student/modify?id=3, post수정 : student/modify
    // 학생 정보 조회 : student/read?id=3

    @GetMapping({"/modify", "/read"})
    public void getModify(@RequestParam Long id, Model model) {
        log.info("조회 {}", id);
        StudentDTO dto = studentService.read(id);
        model.addAttribute("dto", dto);
    }
    @PostMapping("/modify")
    public String postModify(StudentDTO dto, RedirectAttributes rttr) {
        log.info("수정 {}", dto);
        Long id = studentService.update(dto);
        rttr.addAttribute("id", id);
        return "redirect:/student/read";
    }
    
    
    // 학생탈퇴 post : student/remove
    @PostMapping("/remove")
    public String postRemove(StudentDTO dto) {
        log.info("탈퇴 {}", dto);
        studentService.delete(dto.getId());
        return "redirect:/student/list";
    }
    

    // 학생 전체조회 get : student/list
    @GetMapping("/list")
    public void getList(Model model) {
        log.info("전체조회");
        List<StudentDTO> list = studentService.readAll();
        model.addAttribute("list", list);
    }
    

}
