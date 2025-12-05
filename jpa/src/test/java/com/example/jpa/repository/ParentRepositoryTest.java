package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    
    @Test
    public void testInsert(){
        Parent parent = Parent.builder().name("parent1").build();
        parentRepository.save(parent);
        
        Child child1 = Child.builder().name("first").parent(parent).build();
        Child child2 = Child.builder().name("second").parent(parent).build();
        Child child3 = Child.builder().name("third").parent(parent).build();
        childRepository.save(child1);
        childRepository.save(child2);
        childRepository.save(child3);
    }


}
