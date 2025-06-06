package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.movies;
import java.util.List;

public interface moviesRepo extends JpaRepository<movies, Long> {
    List<movies> findAllByOrderByReleaseDateDesc();
    
}
