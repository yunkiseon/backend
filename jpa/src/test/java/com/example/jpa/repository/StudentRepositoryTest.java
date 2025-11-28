package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.constant.Grade;

@SpringBootTest
public class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void deleteTest(){
        // Student student = studentRepository.findById(1l).get();
        // studentRepository.delete(student);
        studentRepository.deleteById(1L);
    }


    @Test
    public void readTest(){
        Student student = studentRepository.findById(2L).get();
        System.out.println(student);
    }

    @Test
    public void readTest2(){
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }
    
    @Test
    public void insertTest(){
        Student student = Student.builder()
        .name("성춘향")
        .addr("부산")
        .gender("F")
        .grade(Grade.JUNIOR)
        .build();
        studentRepository.save(student);
    }
        // delete from 호출 :
        // studentRepository.deleteById(student);
        // studentRepository.delete(student);

        // select * from where id = 1; 같이 하나만 부르기
        // studentRepository.findById(null);
        // select * from
        // studentRepository.findAll();
    
    @Test
    public void updateTest(){
        // entity 
        // update student set 수정 컬럼 = 값 where id = 1; 하기 위해서 찾아야함
        Optional<Student> result = studentRepository.findById(1L); // 여기서의 Id는 @Id 를 쓴 칼럼 기준이다. 그리고 타입까지 줘야함
        Student student = result.get();
        // student.changeName("성춘향");
        student.changeGrade(Grade.FRESHMAN);
        studentRepository.save(student);
        
    }

}
