package com.example.movietalk.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movietalk.member.entitiy.Member;
import com.example.movietalk.member.entitiy.constant.Role;
import com.example.movietalk.member.repository.MemberRepository;
import com.example.movietalk.movie.entitiy.Movie;
import com.example.movietalk.movie.entitiy.MovieImage;
import com.example.movietalk.movie.entitiy.Review;
import com.example.movietalk.movie.repository.MovieImageRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

@SpringBootTest
public class MovieRepositoryTest {
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void memberInsertTest(){
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
            .email("user"+i+"@gmail.com")
            .nickname("user"+ i)
            .password(passwordEncoder.encode("1111"))
            .role(Role.MEMBER)
            .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 영화번호 임의 추출
            long mno = (int) (Math.random() * 100) + 1;

            // 리뷰 사용자 임의 추출
            long mid = (int) (Math.random() * 10) +1;
            Review review = Review.builder()
            .member(Member.builder().mid(mid).build())
            .movie(Movie.builder().mno(mno).build())
            .grade((int)(Math.random()*5)+1)
            .text("review..."+i)
            .build();
            reviewRepository.save(review);
        });
    }

    


    @Test
    public void insertTest(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
            .title("Movie Title" + i)
            .build();
            movieRepository.save(movie);

            // 임의의 이미지 삽입
            int count = (int)(Math.random() * 5) +1;
            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                //java.util.UUID -> 16진수로 겹치지 않는 값을 부여
                .uuid(UUID.randomUUID().toString())
                .movie(movie)
                .ord(j)
                .imgName("test" + j + ".jpg")
                .build();
                movieImageRepository.save(movieImage);
            }
        });

    }

    // 조회
    // mno, 영화 이미지 중 첫번째 이미지, 영화제목, 리뷰수, 리뷰 평균점수, 영화등록일
    
    @Test
    public void movieListTest(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // 영화 상세 조회
    @Test
    public void getMovieWithAllTest(){
        
        Object[] result = movieRepository.getMovieWithAll(100L);
        System.out.println(Arrays.toString(result));
    }
}
