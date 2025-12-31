package com.example.movietalk.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.member.entitiy.Member;
import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.entitiy.Movie;
import com.example.movietalk.movie.entitiy.Review;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public Long insertRow(ReviewDTO dto){
        // dto -> entity
        // Review review = dtoToEntity(dto);
        // return reviewRepository.save(review).getRno(); 아래와 동일
        return reviewRepository.save(dtoToEntity(dto)).getRno();
    }

    public void deleteRow(Long rno){
        reviewRepository.deleteById(rno);
    }

    public Long updateRow(ReviewDTO dto){
        // 업데이트 대상 찾기
        Review review = reviewRepository.findById(dto.getRno()).get();
        // 변경사항 적용할 entity 메소드-> change
        review.changeText(dto.getText());
        review.changeGrade(dto.getGrade());
        // 더티체킹 때문에 알아서 저장됨
        return review.getRno();
    }

    @Transactional(readOnly = true)
    public ReviewDTO getRow(Long rno){
        // Review review = reviewRepository.findById(rno).get();
        // ReviewDTO dto = entityToDTO(review);
        // return dto; 아래와 동일
        return entityToDTO(reviewRepository.findById(rno).get());
    }
    
    
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> getList(Long mno){
        Movie movie = movieRepository.findById(mno).get();
        List<Review> reviews = reviewRepository.findByMovie(movie);
        // List<ReviewDTO> list = new ArrayList<>();
        // reviews.forEach(review -> {
        //     ReviewDTO dto = entityToDTO(review);
        //     list.add(dto);
        // }); 아래와 동일
        List<ReviewDTO> list = reviews.stream()
        .map(review -> entityToDTO(review))
        .collect(Collectors.toList());
        return list;

    }

    private Review dtoToEntity(ReviewDTO dto){
        // ReviewDTO => Review
        Review review = Review.builder()
        .rno(dto.getRno())
        .grade(dto.getGrade())
        .text(dto.getText())
        .movie(Movie.builder().mno(dto.getMno()).build())
        .member(Member.builder().mid(dto.getMid()).build())
        .build();
        return review;
    }




    private ReviewDTO entityToDTO(Review review){
        // Revieew => ReviewDTO
        ReviewDTO reviewDTO = ReviewDTO.builder()
        .rno(review.getRno())
        .grade(review.getGrade())
        .text(review.getText())
        .email(review.getMember().getEmail())
        .mid(review.getMember().getMid())
        .nickname(review.getMember().getNickname())
        .mno(review.getMovie().getMno())
        .createDate(review.getCreateDate())
        .updateDate(review.getUpdateDate())
        .build();
        return reviewDTO;
    }
}
