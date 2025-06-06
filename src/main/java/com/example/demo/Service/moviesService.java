package com.example.demo.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.movies;
import com.example.demo.Repository.moviesRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Iterator;
@Service

public class moviesService {
    private final moviesRepo movieRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=e837128ee3cc897806752f6bab1bbc7d&language=vi-VN&page=1";

    public moviesService(moviesRepo movieRepository) {
        this.movieRepository = movieRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void fetchAndSaveMovies() throws Exception {
        String json = restTemplate.getForObject(API_URL, String.class);
        JsonNode root = objectMapper.readTree(json);
        JsonNode results = root.path("results");

        for (JsonNode movieNode : results) {
            Long movieId = movieNode.path("id").asLong();
            String title = movieNode.path("title").asText();
            String originalTitle = movieNode.path("original_title").asText();
            String releaseDateStr = movieNode.path("release_date").asText();

            LocalDate releaseDate = null;
            if (!releaseDateStr.isEmpty()) {
                releaseDate = LocalDate.parse(releaseDateStr);
            }

            movies movie = new movies();
            movie.setMovieId(movieId);
            movie.setTitle(title);
            movie.setOriginalTitle(originalTitle);
            movie.setReleaseDate(releaseDate);

            movieRepository.save(movie);
        }
    }
    
}
