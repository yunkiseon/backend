package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Student;

// DAO 역할, 기본적인 CRUD 메소드는 이미 정의되어 있음.
// 작성시 enitity 이름과 id의 타입을 넣어줘야 한다.
public interface StudentRepository extends JpaRepository<Student,Long>{

    
}
