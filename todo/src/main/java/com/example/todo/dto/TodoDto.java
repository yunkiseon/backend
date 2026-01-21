package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoDto {
    
    
    private Long id;

    
    private String title;

    
    private boolean completed;

    
    private boolean important;
}
