package com.example.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Builder
@Table(name = "todotbl")
@Entity
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "tinyint DEFAULT 0")
    private boolean completed;

    @Column(columnDefinition = "tinyint DEFAULT 0")
    private boolean important;

    public void changeCompleted(boolean completed) {
        this.completed = completed;
    }
    public void changeImportant(boolean important) {
        this.important = important;
    }
    public void changeTitle(String title) {
        this.title = title;
    }
}
