package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;

@SpringBootTest
public class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void insertTest(){
        Student student = Student.builder()
        .name("홍길동")
        .addr("부산")
        .gender("F")
        .build();
        studentRepository.save(student);
        // delete from 호출 :
        // studentRepository.deleteById(student);
        // studentRepository.delete(student);

        // select * from where id = 1; 같이 하나만 부르기
        // studentRepository.findById(null);
        // select * from
        // studentRepository.findAll();
    }

}
