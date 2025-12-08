package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.Grade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @SequenceGenerator(name = "stu_seq_gen", sequenceName = "stu_seq", allocationSize = 1)
@Builder
@Table(name="stutbl")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity{
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // @Column(name = "sname", length = 50, nullable=false, unique = true)
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @Column
    private String addr;

    @Column(columnDefinition = "varchar(1) CONSTRAINT chk_gender CHECK (gender IN('M','F'))")
    private String gender;

    // grade -> FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    @Enumerated(EnumType.STRING)
    @Column
    private Grade grade;

    @CreationTimestamp
    private LocalDateTime createDateTime1;//create_date_time1 datetime(6), -> hibernate함수
    

    public void changeName(String name) {
        this.name = name;
    }
    public void changeGrade(Grade grade) {
        this.grade = grade;
    }
}
