package com.example.mart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.Member;
import com.example.mart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order>  findByMember(Member member);
}
