package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class movies {
    @Id
    private Long movieId;
    private String title;
    private String originalTitle;
    private LocalDate releaseDate;

    // Constructor, getters, setters

public movies() {
    }

    public movies(Long movieId, String title, String originalTitle, LocalDate releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}   