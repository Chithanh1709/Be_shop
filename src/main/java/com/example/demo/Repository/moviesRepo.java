package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.movies;

public interface moviesRepo extends JpaRepository<movies, Long> {
    // Các phương thức truy vấn tùy chỉnh có thể được thêm vào đây nếu cần
    // Ví dụ: tìm kiếm theo tiêu đề, ngày phát hành, v.v.
    
}
