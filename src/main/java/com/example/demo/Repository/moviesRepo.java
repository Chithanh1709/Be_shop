package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.movies;
import java.util.List;
import java.util.Optional;

public interface moviesRepo extends JpaRepository<movies, Long> {
    List<movies> findAllByOrderByReleaseDateDesc();
    Optional<movies> findById(Long id);
}
