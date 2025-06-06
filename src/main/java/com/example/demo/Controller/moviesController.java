package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.moviesRepo;
import com.example.demo.Service.moviesService;
import com.example.demo.Model.movies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class moviesController {

    @Autowired
    private moviesService moviesService;

    public moviesController(moviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/movies")
    public String syncMovies() {
        try {
            moviesService.fetchAndSaveMovies();
            return "Đã đồng bộ phim thành công!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Đã xảy ra lỗi khi đồng bộ phim: " + e.getMessage();
        }
    }
    
    
}
