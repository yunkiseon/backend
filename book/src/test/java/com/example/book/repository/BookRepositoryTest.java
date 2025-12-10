package com.example.book.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.book.entity.constant.Book;
import com.example.book.entity.constant.QBook;

import jakarta.persistence.EntityNotFoundException;


@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void insert(){
        
        Book book = Book.builder()
        .isbn("A101010")
        .title("파워 자바")
        .author("천인국")
        .price(36000)
        .build();

        bookRepository.save(book);
    }
    @Test
    public void insert2(){
        
        IntStream.rangeClosed(0, 9).forEach(i->{
        Book book = Book.builder()
        .isbn("A101012"+i)
        .title("파워 자바"+i)
        .author("천인국"+i)
        .price(36000)
        .build();

        bookRepository.save(book);
    });
    } 
    @Test
    public void testRead(){
        // bookRepository.findById(1L).orElse(null);
        Book book = bookRepository.findById(1L).orElseThrow();
        // bookRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        // Optional<Book> result = bookRepository.findById(1L);
        // if (result.isPresent()){Book book = result.get();}
        System.out.println(book);
    }

    @Test
    public void testRead2(){
        
        Book book = bookRepository.findByIsbn("A101010").orElseThrow(EntityNotFoundException::new);
        System.out.println(book);
        List<Book> list = bookRepository.findByTitleContaining("파워");
        System.out.println(list);
    }

    @Test
    public void testModify(){
        Book book = bookRepository.findById(1L).orElseThrow();
        book.changePrice(880000);
        bookRepository.save(book);
    }
    @Test
    public void testDeleate(){
        bookRepository.deleteById(10L);
    }

    @Test
    public void testFindby(){
        List<Book> list = bookRepository.findByAuthor("천인국");
        System.out.println("findByAuthor 실행결과" + list);
        list = bookRepository.findByAuthorEndingWith("옹");
        System.out.println("findByAuthorEndingWith실행결과" + list);
        list = bookRepository.findByAuthorStartingWith("홍");
        System.out.println("findByAuthorStartingWith 실행결과" + list);
        list = bookRepository.findByAuthorContaining("파");
        System.out.println("findByAuthorContaining 실행결과" + list);
        list = bookRepository.findByPriceBetween(12000, 35000);
        System.out.println("findByPriceBetween 실행결과" + list);
        
        
    }


    //-----------querydsl 라이브러리 추가,  QuerydslPredicateExecutor<Book> 상속 이후
    // findbytitle query 작성 없이 title로 검색 가능
    @Test
    public void querydslTest(){
        QBook book = QBook.book;
        // 
        System.out.println(bookRepository.findAll(book.title.eq("파워 자바")));
        System.out.println(bookRepository.findAll(book.title.contains("파워")));
        System.out.println(bookRepository.findAll(book.title.contains("파워").and(book.id.gt(3L))));
        System.out.println(bookRepository.findAll(book.title.contains("파워").and(book.id.gt(3L)),Sort.by("id").descending()));
        //where author %천% or title= %파워%
        System.out.println(bookRepository.findAll(book.title.contains("파워").or(book.author.contains("천"))));
        
        // bookRepository.findAll(pageable) 페이지 나누기
        // limit ?,? 를 알아서 잡아줌
        // select count(): 전체 행 개수 세어줌
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(book.id.gt(0L),pageRequest);
        
    }
    
    // 아래는 위의 페이지 나누기에서 사용할 pageReuest를 위한 개념 학습임, querydsl과 관계x
    @Test
    public void pageTest(){
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(pageRequest);
        System.out.println("page size"+result.getSize());
        System.out.println("TotalPages"+result.getTotalPages());
        System.out.println("TotalElements"+result.getTotalElements());
        System.out.println("Content"+result.getContent());
        
    }
}
