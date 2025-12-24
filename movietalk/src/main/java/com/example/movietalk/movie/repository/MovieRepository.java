package com.example.movietalk.movie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.movietalk.movie.entitiy.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    
    //sql과 달리 열린 쪽의 컬럼과 열결된 쪽의 테이블명
    // distinct -> 중복제거, coalesce-> null = 0
    @Query(value = "select m, mi, count(distinct r), avg(coalesce(r.grade,0)) " 
    + "from Movie m left outer join MovieImage mi on mi.movie = m and mi.ord = 0 "
    + "left outer join Review r on r.movie = m group by m, mi", countQuery = "select count(m) from Movie m")        
    Page<Object[]> getListPage(Pageable pageable);

    // 영화상세 조회

    @Query("select m, mi, count(distinct r), avg(coalesce(r.grade,0)) from Movie m "
    + "left outer join MovieImage mi on mi.movie = m "
    + "left outer join Review r on r.movie = m where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}
