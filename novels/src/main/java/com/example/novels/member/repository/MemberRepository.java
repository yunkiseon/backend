package com.example.novels.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novels.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    @EntityGraph(attributePaths = {"roles"}, type = EntityGraphType.LOAD)
    // @Query("select m from Member m where m.email=:email and m.fromSocial=:fromSocial")
    // 쿼리를 안써도 테스트를 해보면 같은 쿼리가 작성이 된다. 생략 가능. 원래는 분명히 쿼리를 써야만 한다. 
    // 그런데 jpa가 쿼리를 만드는 규칙에 맞추어서 아래의 findbyEmail~을 썼기 때문에 쿼리를 생략할 수 있는 것이다.
    Optional<Member> findByEmailAndFromSocial(String email, boolean fromSocial);
    
}
