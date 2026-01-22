package com.example.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.TodoDto;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Tag(name = "Response Todos",description = "Response Todo API")
@Log4j2
@RequestMapping("/todos")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;
    
    // 전체조회 http://localhost:8080/todos + GET
    // 완료 여부 전체조회 http://localhost:8080/todos?completed=true + GET
    // 미완료 여부 전체조회 http://localhost:8080/todos?completed=false + GET

    // @CrossOrigin(origins = "http://localhost:5173/")
    @Operation(summary = "todo 조회", description = "todo 전체 조회 API- 완료 여부 포함 가능")
    @GetMapping("") // required = false 는 필수요소가 아니라는 선언
    public List<TodoDto> getTodoList(@RequestParam(required = false) Boolean completed) {
        log.info("전체 조회 요청");
        List<TodoDto> list = todoService.findCompletedTodos(completed);
        return list;
    }
    

    // 입력 http://localhost:8080/todos/add + POST
    
    @Operation(summary = "todo 입력", description = "todo 입력 API")
    @PostMapping("/add")
    public Long postTodo(@RequestBody TodoDto dto) {
        log.info("Todo 추가", dto);
        Long id = todoService.create(dto);
        return id;
    }
    
    // 수정 http://localhost:8080/todos/1 +PUT
    @Operation(summary = "todo 수정", description = "todo 수정 API")
    @PutMapping("/{id}")
    public Long putTodo(
        @Parameter(description = "수정할 todo id 값", example = "1", required = true) @PathVariable Long id
        , @RequestBody TodoDto dto) {
        //TODO: process PUT request
        log.info("Todo 수정", id,dto);
        dto.setId(id);

        return todoService.update(dto);
        
    }
    // 삭제 http://localhost:8080/todos/1 +DELETE
    @Operation(summary = "todo 삭제", description = "todo 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@Parameter(description = "삭제할 todo id 값", example = "1", required = true) @PathVariable Long id){
        log.info("삭제 {}", id);
        todoService.delete(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    


}
