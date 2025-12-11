package com.example.book.service;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.constant.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    // crud 메소드를 호출하는 서비스 메소드를 작성
    
    public String create(BookDTO dto){
        // bookDTO -> entity 변경필요
        // 1. 직접 코드로 작성
        // 2. ModelMapper 라이브러리 사용
        // Book book = mapper.map(dto, Book.class);
        return bookRepository.save(mapper.map(dto, Book.class)).getTitle();
    }
    // 하나만 조회, 여러 개 조회
    // 검색 : title -> %자바% or isbn -> uinique 이기에 하나만 조회(id 도)
    // sql에서 decode 등을 활용하면 ifelse 등으로 하나의 메소드로 만들 수 도 있을지 모르나, 아직은 못함
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public BookDTO readIsbn(String isbn){
        //findbyid는 만들어져있기에 바로 사용할 수 있지만 title이나 isbn으로 하고 싶다면 직접 bookrepository에서 만들어야 한다.
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();
        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }
    @Transactional(readOnly = true)
    public BookDTO readId(Long id){
        Book book = bookRepository.findById(id).orElseThrow();
        return mapper.map(book, BookDTO.class);
    }
    
    public Long update(BookDTO upDto){
        Book book= bookRepository.findById(upDto.getId()).orElseThrow();
        book.changePrice(upDto.getPrice());
        book.changeDescription(upDto.getDescription());
        // return bookRepository.save(book).getId();
        return book.getId(); // dirty checking
    }
    
    public void delete(Long id){
        bookRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public PageResultDTO<BookDTO> getList(PageRequestDTO pageRequestDTO){
        // pageNumber : 0으로 시작(1page개념)
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()),pageable);
        List<BookDTO> dtoList = result.get()//이 get() 은  Stream<Book> stream으로 바꾸어 돌려준다.
        .map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());

        // 전체 행의 개수
        Long totalCount = result.getTotalElements();
        // 원래는 dto에 빌더를 해야하지만....
        return PageResultDTO.<BookDTO>withAll()
        .dtoList(dtoList)
        .pageRequestDTO(pageRequestDTO)
        .totalCount(totalCount)
        .build();
    }

}
