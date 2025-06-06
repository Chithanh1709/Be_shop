package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.movies;
import com.example.demo.Repository.moviesRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@Service
public class moviesService {
    private final moviesRepo movieRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String BASE_API_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private final String API_KEY = "e837128ee3cc897806752f6bab1bbc7d";
    private final String LANGUAGE = "vi-VN";

    public moviesService(moviesRepo movieRepository) {
        this.movieRepository = movieRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private String buildUrl(int page) {
        return BASE_API_URL + "?api_key=" + API_KEY + "&language=" + LANGUAGE + "&page=" + page;
    }

    public void fetchAndSaveMovies(int page) throws Exception {
        String url = buildUrl(page);
        String json = restTemplate.getForObject(url, String.class);
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
