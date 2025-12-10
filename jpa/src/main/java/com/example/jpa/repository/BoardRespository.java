package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;


public interface BoardRespository extends JpaRepository<Board,Long>{

    // title =?
    List<Board> findByTitle(String title);
    // content=?
    List<Board> findByContent(String content);
    // title like %?
    List<Board> findByTitleEndingWith(String title);
    // title like %?% and id>0 order by id desc
    List<Board> findByTitleContainingAndIdGreaterThanOrderByIdDesc(String title, Long id);

    // writer like %?%
    List<Board> findByWriterContaining(String writer);
    // title like %?% or content like %?%
    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // @Query 사용->entity기준 반드시 대문자
    // ?는 변수의미. ? 말고 변수명을 적는 것도 가능
    @Query("select b from Board b where b.title = :title")
    List<Board> findByTitle2(String title);

    @Query("select b.title, b.writer from Board b where b.title like %:title%")
    List<Object[]> findByTitleConta3(@Param("title") String title);

    @Query("select b from Board b where b.content = content")
    List<Board> findByContent2(String content);
    
    @Query("select b from Board b where b.title like %?1")
    List<Board> findByTitleEndingWith2(String title);

    @Query("select b from Board b where b.title like %?1% and b.id > ?2 order by id desc")
    List<Board> findByTitleAndId(String title, Long id);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining2(String writer);

    @Query("select b from Board b where b.title like %?1% or b.content like %?2%")
    List<Board> findByTitleOrContent(String title, String content);

    //@query + nativeQuery
    @NativeQuery(value = "select * from boardtbl b where b.title like concat('%', :title,'%') and b.id > :id order by id desc")
    List<Board> findByTitleAndId2(String title, Long id);
} 
