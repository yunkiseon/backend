package com.example.movietalk.movie.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movietalk.movie.entitiy.MovieImage;
import com.example.movietalk.movie.entitiy.Movie;


public interface MovieImageRepository extends JpaRepository<MovieImage, Long>{
    // Movie를 기준으로 MovieImage 삭제하기
    @Modifying
    @Query("delete from MovieImage mi where mi.movie = :movie")
    void deleteByMovie(Movie movie);

    @Query("select mi from MovieImage mi where mi.path = :path")
    List<MovieImage> getOldFileImages(String path);
}
