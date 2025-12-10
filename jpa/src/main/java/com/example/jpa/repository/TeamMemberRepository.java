package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;
import java.util.List;


public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
    // select * from team_member tm where tm.team_id =1; 를 여기서도 가능케하고 싶다.
    // 아래를 해주면 가능해진다. 팀 정보로 팀 멤버찾기
    List<TeamMember> findByTeam(Team team);

    // join 쿼리
    //on 안해도 같은것을 매칭해준다. 이미 두 테이블은 각 entity에서 외래키를 사용하여 조인되어있기 때문에
    // m.team t 로 team t 를 선언대체하는 것이 가능해진다. 
    @Query("select m, t from TeamMember m join m.team t where t = :team")
    List<Object[]> findByMemberAndTeam(@Param("team") Team team);
    @Query("select m, t from TeamMember m join m.team t where t.id = :id")
    List<Object[]> findByMemberAndTeam2(@Param("id") Long id);
    // 내부조인의 경우 동일해야지 연결되고 내용출력한다. 만약 member 중 team 이 없는 멤버를 조회하고 싶다면
    // 외부조인을 해야만 한다
    @Query("select m, t from TeamMember m left join m.team t")
    List<Object[]> findByMemberAndTeam3();
}
