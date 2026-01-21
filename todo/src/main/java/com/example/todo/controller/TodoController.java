package com.example.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/todos")
@RestController
public class TodoController {
    
    // 전체조회 http://localhost:8080/todos + GET
    // 완료 여부 전체조회 http://localhost:8080/todos?completed=true + GET
    // 입력 http://localhost:8080/todos/add + POST
    // 수정 http://localhost:8080/todos/1 +PUT
    // 삭제 http://localhost:8080/todos/1 +DELETE
    


}
