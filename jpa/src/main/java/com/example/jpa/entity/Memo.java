package com.example.jpa.entity;

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
@Entity
@Table(name = "memotbl")
public class Memo extends BaseEntity{
    // 테이블 (memotbl)컬럼 : mno,  momo_text, 작성날짜-> create_date, update_date
    // 클래스 필드명 == 테이블 컬럼명 or != 테이블 컬럼명(@Column(name=""))
    // id를 mno, 하나씩 증가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mno")
    private Long id;

    // memo_text 불일치 자바에선 _ 사용 안함 그래서 db 넘길때 대문자쓰면 알아서 _ 됨
    @Column(nullable = false, name = "memoText")
    private String text;


    //text 수정 메소드
    public void changeText(String text){
        this.text = text;
    }

}
