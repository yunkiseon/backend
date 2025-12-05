package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;
import java.util.List;


public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
    // select * from team_member tm where tm.team_id =1; 를 여기서도 가능케하고 싶다.
    // 아래를 해주면 가능해진다. 팀 정보로 팀 멤버찾기
    List<TeamMember> findByTeam(Team team);
}
