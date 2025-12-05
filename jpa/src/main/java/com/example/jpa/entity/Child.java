package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = "parent")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    private Long id;
    // 부모관계는 many 쪽에 설정
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Parent parent;
}
