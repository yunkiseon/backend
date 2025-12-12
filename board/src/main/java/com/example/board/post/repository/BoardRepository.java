package com.example.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.post.entity.Board;
import java.util.List;


public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository{
    
    // witer은 Member 객체 이므로
    // on 구문 생략 기준 : 일치하는 칼럼
    @Query("select b,m from Board b join b.writer m")
    List<Object[]> getBoardWithWriterList();

    // reply 입장에선 board 가 있다. bno 사용해서 댓글 가져오기
    @Query("select b,r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // 하나 조회
    @Query("select b,m,count(r) from Board b left join b.writer m left join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
    
    // 목록 -> 페이지 나누기 필요 그래서 pageable 쓸 것이라고 미리 알려준다.
    // countQuery는 board의 총 갯수를 세기 위한 것이다. group by로 그룹을 borad 단위로 나누었기 때문에 필요하다.
    @Query(value = "select b,m,count(r) from Board b left join b.writer m left join Reply r on r.board = b group by b", 
    countQuery="select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
    
} 
