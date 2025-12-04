package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@Getter
@Entity
public class TeamMember {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id")
    private Team team;
    // joincolumn 은 어느 컬럼과 조인할 것인지 지정하는 것
    //(name = "team_id") 이 부분이 oracle 에선 필수이다. 테이블명_기본키명
    // 만약 텀을 두고 만들면 update로 해두었다면 team_id, team_team_id 두 개가 된다.
    

}
