package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString(exclude = "sportsMember")
public class Locker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;
    
    // 1:1대응이기에 list x
    @OneToOne(mappedBy = "locker")
    private SportsMember sportsMember;
}
