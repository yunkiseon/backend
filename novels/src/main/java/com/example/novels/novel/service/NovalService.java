package com.example.novels.novel.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.novels.novel.dto.NovelDTO;
import com.example.novels.novel.dto.PageRequestDTO;
import com.example.novels.novel.dto.PageResultDTO;
import com.example.novels.novel.entity.Genre;
import com.example.novels.novel.entity.Novel;
import com.example.novels.novel.repository.GradeRepository;
import com.example.novels.novel.repository.NovelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class NovalService {

    private final NovelRepository novelRepository;
    private final GradeRepository gradeRepository;

    // CRUD
    public Long create(NovelDTO dto){
        Novel novel = Novel.builder()
        .author(dto.getAuthor())
        .title(dto.getTitle())
        .publishedDate(dto.getPublishedDate())
        .genre(Genre.builder().id(dto.getGid()).build())
        .available(dto.isAvailable())
        .build();
        return novelRepository.save(novel).getId();
    }

    @Transactional(readOnly = true)
    public NovelDTO getRow(Long id){
        Object[] row = novelRepository.getNovelId(id);
        return entityToDTO((Novel)(row[0]), (Genre)row[1], (Double)row[2]);
    }

    @Transactional(readOnly = true)
    public PageResultDTO<NovelDTO> getList(PageRequestDTO dto){
        Pageable pageable = PageRequest.of(dto.getPage()-1, dto.getSize(), Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(dto.getGenre(), dto.getKeyword(), pageable);

        Function<Object[], NovelDTO> function = en -> entityToDTO((Novel)en[0], (Genre)en[1], (Double)en[2]);

        List<NovelDTO> dtoList = result.get().map(function).collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).pageRequestDTO(dto).totalCount(totalCount).build();
    }

    public Long update(NovelDTO dto){
        // available 변경
        Novel novel = novelRepository.findById(dto.getId()).get();
        novel.changeAvailable(dto.isAvailable());
        return novel.getId();

    }

    public void delete(Long id){
        // 평점 삭제
        gradeRepository.deleteByNovel(id);
        // 도서 삭제
        novelRepository.deleteById(id);

    }

    

    private NovelDTO entityToDTO(Novel novel, Genre genre, Double rating){
        NovelDTO novelDTO = NovelDTO.builder()
        .id(novel.getId())
        .title(novel.getTitle())
        .author(novel.getAuthor())
        .available(novel.isAvailable())
        .publishedDate(novel.getPublishedDate())
        .gid(genre.getId())
        .genreName(genre.getName())
        .rating(rating != null ? rating.intValue() : 0)
        .build();
        return novelDTO;
    }
}


