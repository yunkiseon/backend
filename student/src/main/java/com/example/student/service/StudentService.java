package com.example.student.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    // service 클래스의 목적 :CRUD 작업을 위한 메소드 호출
    // 호출 후 받은 결과를 컨트롤러로 리턴
    public String insert(StudentDTO dto){
        // entity가 필수
        Student student = modelMapper.map(dto, Student.class);
        return studentRepository.save(student).getName();
    }
    public StudentDTO read(Long id){
        //
        Student student = studentRepository.findById(id).orElseThrow();
        return modelMapper.map(student, StudentDTO.class);
    }
    public List<StudentDTO> readAll(){
        List<Student> result = studentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        //entity -> dto
        List<StudentDTO> list = new ArrayList<>();
        for (Student student : result) {
            list.add(modelMapper.map(student, StudentDTO.class));
        }
        return list;
    }
    public Long update(StudentDTO dto){
        // 업데이트할 대상 찾기 
        Student student = studentRepository.findById(dto.getId()).orElseThrow();
        // 업데이트 
        student.changeGrade(dto.getGrade());
        student.changeName(dto.getName());
        return studentRepository.save(student).getId();
    }
    public void delete(Long id){
        studentRepository.deleteById(id);;
    }
}
