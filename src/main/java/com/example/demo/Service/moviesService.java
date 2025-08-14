package com.example.demo.Service;

import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.movies;
import com.example.demo.Repository.moviesRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class moviesService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(moviesService.class);

    private final moviesRepo movieRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String BASE_API_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private final String API_KEY = "e837128ee3cc897806752f6bab1bbc7d";
    private final String LANGUAGE = "vi-VN";

    @Autowired
    public moviesService(moviesRepo movieRepository) {
        this.movieRepository = movieRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private String buildUrl(int page) {
        return BASE_API_URL + "?api_key=" + API_KEY + "&language=" + LANGUAGE + "&page=" + page;
    }

    @Transactional
    public void fetchAndSaveMovies(int page) {
        try {
            String url = buildUrl(page);
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");

            for (JsonNode movieNode : results) {
                Long movieId = movieNode.path("id").asLong();
                Optional<movies> existingMovie = movieRepository.findById(movieId);
                movies movie = existingMovie.orElse(new movies());

                movie.setMovieId(movieId);
                movie.setTitle(movieNode.path("title").asText());
                movie.setOriginalTitle(movieNode.path("original_title").asText());
                String releaseDateStr = movieNode.path("release_date").asText();
                movie.setReleaseDate(releaseDateStr.isEmpty() ? null : LocalDate.parse(releaseDateStr));
                movie.setOverview(movieNode.path("overview").asText());
                movie.setPosterPath(movieNode.path("poster_path").asText());
                movie.setGenreIds(movieNode.path("genre_ids").isArray()
                        ? objectMapper.convertValue(movieNode.path("genre_ids"), objectMapper.getTypeFactory().constructCollectionType(List.class, String.class))
                        : null);
                movie.setPopularity((float) movieNode.path("popularity").asDouble());
                movie.setVoteAverage((float) movieNode.path("vote_average").asDouble());
                movie.setVoteCount(movieNode.path("vote_count").asInt());

                movieRepository.save(movie);
                logger.info("Saved/Updated movie: {}", movie.getTitle());
            }
        } catch (Exception e) {
            logger.error("Error fetching and saving movies: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch and save movies", e);
        }
    }

    public List<Map<String, Object>> fetchLiveMoviesWithPoster(int page) {
        try {
            String url = buildUrl(page);
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");

            List<Map<String, Object>> movieList = new ArrayList<>();
            for (JsonNode movieNode : results) {
                Map<String, Object> movie = new HashMap<>();
                movie.put("movieId", movieNode.path("id").asLong());
                movie.put("title", movieNode.path("title").asText());
                movie.put("originalTitle", movieNode.path("original_title").asText());
                movie.put("releaseDate", movieNode.path("release_date").asText());
                movie.put("posterPath", movieNode.path("poster_path").asText());
                movie.put("overview", movieNode.path("overview").asText());
                movie.put("genreIds",
                        movieNode.path("genre_ids").isArray()
                                ? objectMapper.convertValue(movieNode.path("genre_ids"), int[].class)
                                : null);
                movie.put("popularity", movieNode.path("popularity").asDouble());
                movie.put("voteAverage", movieNode.path("vote_average").asDouble());
                movie.put("voteCount", movieNode.path("vote_count").asInt());
                movieList.add(movie);
            }
            return movieList;
        } catch (Exception e) {
            logger.error("Error fetching live movies: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch live movies", e);
        }
    }

    public List<movies> getAllMovies() {
        return movieRepository.findAll();
    }
    
    @Transactional
    public movies addMovie(movies movie) {
        Optional<movies> existingMovie = movieRepository.findById(movie.getMovieId());
        if (existingMovie.isPresent()) {
            throw new RuntimeException("ID đã có trong DB: " + movie.getMovieId());
        }

        return movieRepository.save(movie);
    }

    @Transactional
    public movies updateMovie(Long movieId, movies movie) {
        Optional<movies> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            movies updatedMovie = existingMovie.get();
            updatedMovie.setTitle(movie.getTitle());
            updatedMovie.setOriginalTitle(movie.getOriginalTitle());
            updatedMovie.setReleaseDate(movie.getReleaseDate());
            updatedMovie.setOverview(movie.getOverview());
            updatedMovie.setPosterPath(movie.getPosterPath());
            updatedMovie.setGenreIds(movie.getGenreIds());
            updatedMovie.setPopularity(movie.getPopularity());
            updatedMovie.setVoteAverage(movie.getVoteAverage());
            updatedMovie.setVoteCount(movie.getVoteCount());
            return movieRepository.save(updatedMovie);
        } else {
            throw new RuntimeException("Movie not found with id: " + movieId);
        }
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("Movie not found with id: " + movieId);
        }
        movieRepository.deleteById(movieId);
        logger.info("Deleted movie with id: {}", movieId);
    }

}
