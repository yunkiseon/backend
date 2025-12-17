package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Page<Object[]> list(String type, String keyword, Pageable pageable);//하나가 아니라 여러개를 들고 오려면[]을 써야한다.
    Object[] getBoardByBno(Long bno);

}
