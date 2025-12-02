package com.example.student.service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.constant.Grade;

import groovy.transform.ToString;

@Disabled
@SpringBootTest
public class StudentServiceTest {
    // service 도 test하는게 좋다.
    @Autowired
    private StudentService studentService;

    @Test
    public void testInsert(){
        StudentDTO dto = StudentDTO.builder()
        .name("홍길동")
        .gender("M")
        .addr("종로")
        .grade(Grade.FRESHMAN)
        .build();
        System.out.println(studentService.insert(dto));
    }
    @Test
    public void testRead(){
        
        System.out.println(studentService.read(1L));
    }
    @Test
    public void testReadAll(){
        
        List<StudentDTO> list = studentService.readAll();
        for (StudentDTO studentDTO : list) {
            System.out.println(studentDTO);
        }
    }
    
    @Test
    public void testUpdate(){
        // Service에 있는 changeName, grade 등은 바꿀 수 있는 것들이라고 생각하자
        // 그리고 바꿀 Id는 필수이다.
         StudentDTO dto = StudentDTO.builder()
         .id(3L)
        .name("홍길동")
        .grade(Grade.JUNIOR)
        .build();
        studentService.update(dto);

    }
    @Test
    public void testDelete(){
        
        studentService.delete(2L);
    }
}
