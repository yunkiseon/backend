package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "childs")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Child> childs = new ArrayList<>();
}
