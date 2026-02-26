package com.example.ai.domain.request;

// record =dto 역할
public record AdCopyRequest(
    String name,
    String brand,
    String strength,
    String tone,
    String keyword,
    String value) {
    
}
