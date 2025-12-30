package com.example.movietalk.movie.entitiy;

import java.util.ArrayList;
import java.util.List;

import com.example.movietalk.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movieImages")
@Entity
public class Movie extends BaseEntity{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long mno;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private List<MovieImage> movieImages = new ArrayList<>();

    public void addImage(MovieImage movieImage){
        movieImage.setOrd(this.movieImages.size());
        movieImages.add(movieImage);

    }
    public void changeTitle(String title) {
        this.title = title;
    }

}
