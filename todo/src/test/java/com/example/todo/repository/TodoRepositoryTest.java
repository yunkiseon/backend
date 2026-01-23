package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.todo.entity.Todo;

@Disabled
@SpringBootTest
public class TodoRepositoryTest {
    
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void inserTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Todo todo = Todo.builder()
            .title("오늘의 할 일" + i)
            .completed(i % 2 == 0 ? true : false)
            .important(i % 2 == 0 ? true : false)
            .build();
            todoRepository.save(todo);
        });
    }
    
    // 전체 조회
    @Test
    public void readAllTest(){
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // 완료 여부에 따른 조회
    @Test
    public void readAllCompleteTest(){
        Pageable pageable = PageRequest.of(0, 10,Sort.by("id").descending());
        todoRepository.findByCompleted(true,pageable)
        .forEach(todo -> System.out.println(todo));
    }

    

}
