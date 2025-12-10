package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Item;

public interface ItemRespository extends JpaRepository<Item,String>{
    
    // 집계함수 사용
    @Query("select count(i), sum(i.itemPrice), avg(i.itemPrice), max(i.itemPrice), min(i.itemPrice) from Item i")
    List<Object[]> aggr();
}
