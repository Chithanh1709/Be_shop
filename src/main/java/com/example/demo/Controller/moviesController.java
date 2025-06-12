package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.moviesRepo;
import com.example.demo.Service.moviesService;
import com.example.demo.Model.movies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
public class moviesController {

    @Autowired
    private moviesService moviesService;

    @Autowired
    private moviesRepo moviesRepo;

    public moviesController(moviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/movies")
public String syncMovies(@RequestParam(defaultValue = "1") int page) {
    try {
        moviesService.fetchAndSaveMovies(page);
        return "Đã đồng bộ phim thành công (trang " + page + ")!";
    } catch (Exception e) {
        e.printStackTrace();
        return "Đã xảy ra lỗi khi đồng bộ phim: " + e.getMessage();
    }
}


// @GetMapping("/movies/sorted")
// public List<movies> getAllMoviesSorted() {
//     return moviesRepo.findAllByOrderByReleaseDateDesc();
// }

@GetMapping("/movies/live")
public ResponseEntity<List<Map<String, Object>>> getLiveMovies() {
    try {
        List<Map<String, Object>> movies = moviesService.fetchLiveMoviesWithPoster(1);
        return ResponseEntity.ok(movies);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
    

