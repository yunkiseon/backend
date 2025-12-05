package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "team")
@Builder
@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class TeamMember {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;

    // @ManyToOne(optional = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    //(name = "team_id") 이 부분이 oracle 에선 필수이다. 테이블명_기본키명
    // 만약 텀을 두고 만들면 update로 해두었다면 team_id, team_team_id 두 개가 된다.
    
    public void changeTeam(Team team) {
        this.team = team;
    }

}
