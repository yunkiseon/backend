package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Item;

public interface ItemRespository extends JpaRepository<Item,String>{
    
}
