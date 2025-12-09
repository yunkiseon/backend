package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder 
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categoryItems")
@Getter
@Setter
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 관계를 jpa 에게 직접 실행-> 칼럼추가가 어려워서 실무적용에 어렵다.
    // @Builder.Default
    // @ManyToMany
    // @JoinTable(name = "category_item", joinColumns = @JoinColumn(name ="category_id"), inverseJoinColumns = @JoinColumn(name="item_id"))
    // private List<Item> items = new ArrayList<>();
    // 직접 manytoone 만들기

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();
}
