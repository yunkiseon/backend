package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.constant.Book;
import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book,Long>{
    // findby~는  모두 == 의 의미만 가진다. where title = '자바' 식으로 말이다. like가 아님
    // 그래서 Containing 을 붙여야함
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContaining(String title);
}
