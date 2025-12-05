package com.example.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;



    @Test
    public void insertTest(){

        Team team = Team.builder().name("team1").build();
        teamRepository.save(team);
        // 조인되어서 이제는 아래로만으로는 만들 수 없다.팀이 필수
        TeamMember member = TeamMember.builder().name("홍길동").team(team).build();
        teamMemberRepository.save(member);
    }
    @Test
    public void insertTest2(){
        
        // 이미 있는 팀을 사용하는 test.
        // Team team = Team.builder().id(1L).build();
        Team team = teamRepository.findById(3L).get();
        
        TeamMember member = TeamMember.builder().name("홍길동").team(team).build();
        teamMemberRepository.save(member);
    }
    @Test
    public void insertTest3(){
        
        // 팀만 만들기
        // Team team = Team.builder().id(1L).build();
        Team team = Team.builder().name("team3").build();
        teamRepository.save(team);
        
    }
    
    @Test
    public void readTest(){
        // 아래는 객체 생성이지 DB의 select 가 아니다. 때문에 name이 제대로 출력되지 않는다. 
        // Team team = Team.builder().id(1L).build();// Team(id=1, name=null)
        Team team = teamRepository.findById(1L).get();//Team(id=1, name=team1)
        System.out.println(team); 
        // 팀원 정보 추출
        // 외래키가 적용된 테이블이기 때문에 join(지금은 내부조인)을 바로 해서 코드 실행
        TeamMember teamMember = teamMemberRepository.findById(1L).get();
        // TeamMember(id=1, name=홍길동, team=Team(id=1, name=team1))
        // 연관관계의 경우 ToString을 하지 말아야함 순환참조 등이 날 수 있기 때문이다. 
        // 그래서 teammember.java의 tostring에 exclude = team을 해주어야한다.
        // 그러면 팀의 정보가 빠져서 TeamMember(id=1, name=홍길동) 만 출력된다.
        // fetch lazy 를 하면 join이 완전히 빠진다.  
        System.out.println(teamMember);

        // 팀원입장에서 팀 조회 : 팀 명 : team1
        // fetch lazy 를 하고나면 아래의 구문은 오류난다
        // 조인이 사라지면서 팀 정보를 가지고 올 수 없어졌기 때문이다. 
        // System.out.println("팀 명 : "+teamMember.getTeam().getName());

        // 팀 -> 팀원조회 불가능한 상황. sql에선 쉽게 가능하지만, 
        // 객체에선 그 개념 적용이 안된다. 관계를 따로 만들어야한다. -> one to many로 하면 됨
    }

    @Transactional
    @Test
    public void readTest4(){
        TeamMember member = teamMemberRepository.findById(4L).get();
        System.out.println(member);
        // @ManyToOne(optional = false) 였다면 가능
        // @ManyToOne(optional = false, fetch = FetchType.LAZY)에선 불가능
        // transactional을 붙이면 가능
        System.out.println(member.getTeam());
    }


    @Test
    public void updateTest(){
        Team team = teamRepository.findById(2L).get();
        team.changeName("SSG");
        teamRepository.save(team);
        System.out.println(team); 
        TeamMember teamMember = teamMemberRepository.findById(2L).get();
        teamMember.changeTeam(Team.builder().id(2L).build());
        System.out.println(teamMemberRepository.save(teamMember));
    }
    @Test
    public void deleteTest(){
        // 1. 팀원삭제부터 해야함
        // teamMemberRepository.deleteById(null);
        // 2. 혹은 삭제하려고 하는 팀의 팀원들 팀을 변경 시키면 되긴 함
        //  1)팀원을 팀 정보를 이용해서 찾기
        //repository에 가서 만들어주면 된다. 그러면 teamMemberRepository.findByTeam이 가능해짐
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(1L).build());
        //  2) 바꾸기
        result.forEach(m ->{
            m.changeTeam(Team.builder().id(2L).build());
            teamMemberRepository.save(m);
        });
        // 팀삭제
        teamRepository.deleteById(1L);
    }
    @Test
    public void deleteTest2(){
        // 만약 OneToMany에 cascade = CascadeType.ALL 를 해놓았다면 팀삭제 시 팀원들까지 모두 삭제된다.
        // 1. 팀원삭제부터 해야함
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(2L).build());
        result.forEach((m->{
            teamMemberRepository.delete(m);
        }));
        // 팀삭제
        teamRepository.deleteById(2L);
    }

    // 팀 입장에서 멤버를 조회하기 
    @Transactional
    @Test
    public void readTest2(){
        Team team = teamRepository.findById(3L).get();
        System.out.println(team);//lazy 에러가 뜸-> Tostring(exclude member해줘야함)
        // sekect * from teamtbl where id=3
        System.out.println(team.getMembers());
        // 이러면 도 내부조인과 외부조인 모두 일어난다. 그 이유는 객체에서 manytoone,onetomany 모두 실행해서 그렇다
        // 때문에 실질적인 주인이 누구인지 알려주어야 한다. 
        // 당장 외래키를 manyToOne에 넣었기 때문에 OneToMany에  mappedby를 넣어주어서 manytoone이 주인임을 명시해야한다
        // select * from team_member where team_id=3
    }
    
    @Test
    public void readTest3(){
        Team team = teamRepository.findById(3L).get();
        //left join을 하기 위해서  @OneToMany(mappedBy = "team", fetch = FetchType.EAGER 을넣고 transactional을 지운다
        // 원투매니는 기본적으로 레프트조인이나 기본값은 조인구문으로 '처리'하진 않는다.
        //  그래서 바로 처리하라는 의미로 fetchType.eager하는 것.
        System.out.println(team);
        // System.out.println(team.getMembers());
    }
}
