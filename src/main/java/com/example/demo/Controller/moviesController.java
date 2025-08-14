package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.moviesRepo;
import com.example.demo.Service.moviesService;
import com.example.demo.Model.movies;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class moviesController {

    @Autowired
    private moviesService movieService;

    @GetMapping
    public List<movies> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/live")
    public List<Map<String, Object>> fetchLiveMovies(@RequestParam(defaultValue = "1") int page) {
        return movieService.fetchLiveMoviesWithPoster(page);
    }

    @PostMapping
    public ResponseEntity<movies> addMovie(@RequestBody movies movie) {
        movies addedMovie = movieService.addMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<movies> updateMovie(@PathVariable Long movieId, @RequestBody movies movie) {
        movies updatedMovie = movieService.updateMovie(movieId, movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}


    

