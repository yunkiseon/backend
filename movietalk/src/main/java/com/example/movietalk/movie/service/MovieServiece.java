package com.example.movietalk.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.MovieImageDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.entitiy.Movie;
import com.example.movietalk.movie.entitiy.MovieImage;
import com.example.movietalk.movie.repository.MovieRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class MovieServiece {
    private final MovieRepository movieRepository;

    // 전체조회
    @Transactional(readOnly = true)
    public PageResultDTO<MovieDTO> getMovieList(PageRequestDTO pageRequestDTO){
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), 
                Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        // [Movie(mno=100, title=Movie Title100), 1, 2.0, 
        // MovieImage(inum=318, uuid=5d1246c5-34b6-4da4-a106-705057c4edf7, path=null, 
        // imgName=test0.jpg, ord=0)] 가 하나씩 10개 출력
        // entity <-> dto 시 동일한 정보 형태라면 ModelMapper을 쓰면된다. 
        // 그렇지 않은 경우 메소드를 하나 만들어야 한다. => 그래서 만든게 entityToDTO
        // List<MovieDTO> dtolist = new ArrayList<>();
        // result.forEach(obj -> {
        //     MovieDTO dto = entitiyToDTO((Movie)obj[0], List.of((MovieImage) obj[1]), (Long)obj[2], (Double)obj[3]);
        //     dtolist.add(dto);
        // }); 아래와 동일
        // 여기서 순서는 짜둔 쿼리와 순서가 같아야 한다.
        Function<Object[], MovieDTO> function = (obj -> entitiyToDTO((Movie)obj[0], List.of((MovieImage) obj[1]), (Long)obj[2], (Double)obj[3]));

        List<MovieDTO> dtolist = result.stream().map(function).collect(Collectors.toList());

        Long totalCount = result.getTotalElements();
        return PageResultDTO.<MovieDTO>withAll()
                .dtoList(dtolist)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }
    
    // 상세조회
    @Transactional(readOnly = true)
    public void getMovie(Long mno){
        movieRepository.getMovieWithAll(mno);
    }

    private MovieDTO entitiyToDTO(Movie movie, List<MovieImage> mImage, Long reviewCnt, Double avg){
        MovieDTO movieDTO = MovieDTO.builder()
        .mno(movie.getMno())
        .title(movie.getTitle())
        .avg(avg)
        .reviewCnt(reviewCnt)
        .createDate(movie.getCreateDate())
        .build();

        // list<MovieImage> => list<MOvieImageDTO>
        List<MovieImageDTO> imageDTOs = mImage.stream().map(movieImage -> {
            return MovieImageDTO.builder()
            .inum(movieImage.getInum())
            .imgName(movieImage.getImgName())
            .uuid(movieImage.getUuid())
            .path(movieImage.getPath())
            .build();
        }).collect(Collectors.toList());


        movieDTO.setMovieImages(imageDTOs);
        return movieDTO;
    }
}
