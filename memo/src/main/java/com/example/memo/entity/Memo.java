package com.example.memo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EntityListeners(value = AuditingEntityListener.class)
@Entity
@Table(name = "memotbl")
public class Memo {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mno")
    private Long id;

    @Column(nullable = false, name = "memoText")
    private String text;

    @CreatedDate
    @Column(name = "createDate")
    private LocalDateTime createDate;

    
    @LastModifiedDate
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    //text 수정 메소드
    public void changeText(String text){
        this.text = text;
    }

}
