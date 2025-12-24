package com.example.movietalk.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movietalk.member.entitiy.Member;
import com.example.movietalk.movie.entitiy.Movie;
import com.example.movietalk.movie.entitiy.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    //rno 가 아닌 mno 기준으로 리뷰 조회
    // fetch -> fetchtype.eager로 처리하고 나머지는 lazy로 처리
    // load -> fetchtype.eager로 처리하고 나머지는 entity 클래스에 명시된 방법 or 기본방법
    @EntityGraph(attributePaths = {"member"}, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 리뷰작성자를 기준으로 리뷰 삭제
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member);
}
