package com.example.book.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;

@SpringBootTest
public class BookServiceTest {
    
    @Autowired
    private BookService bookService;

    @Test
    public void testList(){
        PageRequestDTO dto = PageRequestDTO.builder()
        .page(11)
        .size(10)
        .build();
        PageResultDTO<BookDTO> result = bookService.getList(dto);
        //화면에 보여줄 목록
        System.out.println(result.getDtoList());
        System.out.println("사용자 요청번호"+result.getCurrent());
        System.out.println("전체 행의 개수"+result.getTotalCount());
        System.out.println("전체 페이지 수"+result.getTotalPage());
        System.out.println("하단에 보여줄 페이지 목록"+result.getPageNumList());
    }
    @Test
    public void querydslTest2(){
        PageRequestDTO dto = PageRequestDTO.builder()
        .page(1)
        .size(10)
        .type("t")
        .keyword("붕어빵")
        .build();
        PageResultDTO<BookDTO> result = bookService.getList(dto);

        System.out.println(result.getDtoList());
        System.out.println("검색결과 행 개수 : " + result.getTotalCount());
    }
}
