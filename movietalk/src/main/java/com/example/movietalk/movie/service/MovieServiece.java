package com.example.movietalk.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.entitiy.Movie;
import com.example.movietalk.movie.entitiy.MovieImage;
import com.example.movietalk.movie.entitiy.Review;
import com.example.movietalk.movie.repository.MovieImageRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class MovieServiece {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

   
    // 영화 삭제
    public void deleteRow(Long mno){
        // 리뷰 삭제
        Movie movie = movieRepository.findById(mno).get();
        reviewRepository.deleteByMovie(movie);
        // 영화이미지 제거
        movieImageRepository.deleteByMovie(movie);
        // 영화삭제
        movieRepository.delete(movie);
    }

    // 영화수정
    public Long updateRow(MovieDTO dto){
        
        // 영화 제목 변경
        Movie movie = movieRepository.findById(dto.getMno()).get();
        movie.changeTitle(dto.getTitle());
        // 더티체킹으로 세이브안해도 적용됨
        //영화이미지 제거
        movieImageRepository.deleteByMovie(movie);
        //이미지추가
        
        movie = dtoToEntity(dto);
        movie.getMovieImages().forEach(img -> movieImageRepository.save(img));
        return movie.getMno();
    }

    @Transactional(readOnly = true)
    public MovieDTO getRow(Long mno){
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        // 위의 메소드는 dto 값을 호출하는데 중복되는 무비이미지가 있는 경우
        // 하나의 무비와 여러개의 이미지를 부르면 된다. 
        Movie movie = (Movie)result.get(0)[0];
        // img
        List<MovieImage> movieImages = result.stream().map(en -> (MovieImage)en[1]).collect(Collectors.toList());
        // review 수, 평점 첫번째 배열의 첫 값 가져오기
        Long reviewCnt = (Long)result.get(0)[2];
        Double avg = (Double)result.get(0)[3];

        return entitiyToDTO(movie, movieImages, reviewCnt, avg);
        
    }

    // 1:n관계 적용 + cascade 적용
    // movie에 movieImages가 생겼고 ord
    public String register(MovieDTO dto){
        Movie movie = dtoToEntity(dto);
        return movieRepository.save(movie).getTitle();
    }
    private Movie dtoToEntity(MovieDTO dto){
        Movie movie = Movie.builder()
        .mno(dto.getMno())
        .title(dto.getTitle())
        .build();

        // list<MovieImageDTO> => list<movieImage>
        List<MovieImageDTO> imageDTOs = dto.getMovieImages();
        if (imageDTOs != null && imageDTOs.size() > 0) {
            imageDTOs.stream().forEach(movieImage -> {
            MovieImage image = MovieImage.builder()
                .inum(movieImage.getInum())
                .imgName(movieImage.getImgName())
                .uuid(movieImage.getUuid())
                .path(movieImage.getPath())
                .movie(movie)
                .build();
                movie.addImage(image);
            });
        }
        return movie;
    }
    // public Long register(MovieDTO dto){
    //     Map<String, Object> entityMap = dtoToEntity(dto);
    //     // 영화 정보 저장 ->
    //     Movie movie = (Movie)entityMap.get("movie");
    //     movieRepository.save(movie);
    //     // 영화 이미지 저장 -> 
    //     List<MovieImage> imgList = (List<MovieImage>)entityMap.get("imgList");
    //     imgList.forEach(img -> {
    //         movieImageRepository.save(img);
    //     });
    //     return movie.getMno();
    // }

    // private Map<String, Object> dtoToEntity(MovieDTO dto){
    //     Map<String, Object> entityMap = new HashMap<>();

    //     Movie movie = Movie.builder()
    //     .mno(dto.getMno())
    //     .title(dto.getTitle())
    //     .build();
    //     entityMap.put("movie", movie);

    //     // list<MovieImageDTO> => list<movieImage>
    //     List<MovieImageDTO> imageDTOs = dto.getMovieImages();
    //     if (imageDTOs != null && imageDTOs.size() > 0) {
    //         List<MovieImage> imageList = imageDTOs.stream().map(movieImage -> {
    //             return MovieImage.builder()
    //             .inum(movieImage.getInum())
    //             .imgName(movieImage.getImgName())
    //             .uuid(movieImage.getUuid())
    //             .path(movieImage.getPath())
    //             .movie(movie)
    //             .build();
    //         }).collect(Collectors.toList());
    //         entityMap.put("imgList", imageList);
            
    //     }
    //     return entityMap;
    // }



    
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
