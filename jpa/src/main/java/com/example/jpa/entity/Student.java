package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;

// @SequenceGenerator(name = "stu_seq_gen", sequenceName = "stu_seq", allocationSize = 1)
@EntityListeners(value = AuditingEntityListener.class)
@Builder
@Table(name="stutbl")
@Entity
public class Student {
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // @Column(name = "sname", length = 50, nullable=false, unique = true)
    @Column(columnDefinition = "varchar(50) not null unique")
    private String name;

    @Column
    private String addr;

    @Column(columnDefinition = "varchar(1) CONSTRAINT chk_gender CHECK (gender IN('M','F'))")
    private String gender;

    @CreationTimestamp
    private LocalDateTime createDateTime1;//create_date_time1 datetime(6), -> hibernate함수
    
    @CreatedDate
    private LocalDateTime createDateTime2;//create_date_time2 datetime(6), -> springframework 함수
}
