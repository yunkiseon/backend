package com.example.club.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.Member;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, String>{
    // 구글 로그인 접근 여부
    // left join 으로 처리 됨
    // roles = member리포지토리의 변수 roles, 무조건 처음에 가져와라, 즉 fetch 시점을 선언하는 것과도 같다. 
    // 때문에, lazy 해결법으로 transactional 사용과 entitygraph도 있는 것이다.
    @EntityGraph(attributePaths = {"roles"}, type = EntityGraphType.LOAD)
    // @Query("select m from Member m where m.email=:email and m.fromSocial=:fromSocial")
    // 쿼리를 안써도 테스트를 해보면 같은 쿼리가 작성이 된다. 생략 가능. 원래는 분명히 쿼리를 써야만 한다. 
    // 그런데 jpa가 쿼리를 만드는 규칙에 맞추어서 아래의 findbyEmail~을 썼기 때문에 쿼리를 생략할 수 있는 것이다.
    Optional<Member> findByEmailAndFromSocial(String email, boolean fromSocial);
}
