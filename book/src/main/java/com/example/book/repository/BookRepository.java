package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.constant.Book;
import com.example.book.entity.constant.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book,Long>, QuerydslPredicateExecutor<Book> {
    //querydsl 은 interface임
    // findby~는  모두 == 의 의미만 가진다. where title = '자바' 식으로 말이다. like가 아님
    // 그래서 Containing 을 붙여야함
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContaining(String title);
    // where author = ''
    List<Book> findByAuthor(String author);
    // where author like "%영" jpa querymethods 참고
    List<Book> findByAuthorEndingWith(String author);
    // where author like "박%"
    List<Book> findByAuthorStartingWith(String author);
    // where author like "%진수%"
    List<Book> findByAuthorContaining(String author);
    
    // 도서가격이  12000 이상 35000 이하
    List<Book> findByPriceBetween(int startPrice, int endPrice);


    public default Predicate makePredicate(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QBook book = QBook.book;

        builder.and(book.id.gt(0));//where b.id > 0

        if (type == null) {
            return builder;
        }
        if (type.equals("t")) {
            builder.and(book.title.contains(keyword));
        } else {
            builder.and(book.author.contains(keyword));
        }
        return builder;

    }
    
}
