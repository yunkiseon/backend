package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;




@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membertbl")
@Entity
public class Member extends BaseEntity{
    // 아이디(必), 이름(必), 나이(必), 역할(MEMBER, ADMIN 中 1), 가입일자, 수정일자, 자기소개
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    // @Column(length=2000) 
    // 이것 말고도 DB 에서의 개념 중 CLOB/BLOB 라는 개념이 있다. 
    // LOB(Large Object) CLOB: 문자 대용량 데이터 / BLOB : 바이트 대용량 데이터
    @Lob
    private String description;

   public void changeRole(RoleType role){
    this.role = role;
   }
}
