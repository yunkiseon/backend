package com.example.book.service;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.constant.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    // crud 메소드를 호출하는 서비스 메소드를 작성
    
    public Long create(BookDTO dto){
        // bookDTO -> entity 변경필요
        // 1. 직접 코드로 작성
        // 2. ModelMapper 라이브러리 사용
        // Book book = mapper.map(dto, Book.class);
        return bookRepository.save(mapper.map(dto, Book.class)).getId();
    }
    // 하나만 조회, 여러 개 조회
    // 검색 : title -> %자바% or isbn -> uinique 이기에 하나만 조회(id 도)
    // sql에서 decode 등을 활용하면 ifelse 등으로 하나의 메소드로 만들 수 도 있을지 모르나, 아직은 못함
    public List<BookDTO> readTitle(String title){
        //findbyid는 만들어져있기에 바로 사용할 수 있지만 title이나 isbn으로 하고 싶다면 직접 bookrepository에서 만들어야 한다.
        List<Book> result = bookRepository.findByTitleContaining(title);
        // List<Book> => List<BookDTO> 변경 후 리턴
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        //     list.add(mapper.map(book, BookDTO.class));
        // });
        return result.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());

    }
    public BookDTO readIsbn(String isbn){
        //findbyid는 만들어져있기에 바로 사용할 수 있지만 title이나 isbn으로 하고 싶다면 직접 bookrepository에서 만들어야 한다.
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();
        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }

    public BookDTO readId(Long id){
        Book book = bookRepository.findById(id).orElseThrow();
        return mapper.map(book, BookDTO.class);
    }

    public Long update(BookDTO upDto){
        Book book= bookRepository.findById(upDto.getId()).orElseThrow();
        book.changePrice(upDto.getPrice());
        return bookRepository.save(book).getId();
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
