package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class TodoService {
    
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public Long create(TodoDto dto){
        // dto -> entity
        Todo todo = modelMapper.map(dto, Todo.class);
        return todoRepository.save(todo).getId();
        
    }
    public Long update(TodoDto dto){
        Todo todo = todoRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        todo.changeTitle(dto.getTitle());
        todo.changeCompleted(dto.isCompleted());
        todo.changeImportant(dto.isImportant());
        return todo.getId();

    }
    public void delete(Long id){
        todoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TodoDto> findCompletedTodos(boolean completed){
        List<Todo> result = todoRepository.findByCompleted(completed);
        // entity => dto
        return result.stream().map(todo -> modelMapper.map(todo, TodoDto.class))
        .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public List<TodoDto> findImportantTodos(boolean important){
        List<Todo> result = todoRepository.findByImportant(important);
        return result.stream().map(todo -> modelMapper.map(todo, TodoDto.class))
        .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<TodoDto> findTodos(){
        List<Todo> result = todoRepository.findAll();
        return result.stream().map(todo -> modelMapper.map(todo, TodoDto.class))
        .collect(Collectors.toList());
    }
}
