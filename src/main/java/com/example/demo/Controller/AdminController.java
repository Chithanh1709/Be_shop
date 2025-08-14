package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Users;
import com.example.demo.Repository.userRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AdminController {
    @Autowired
    private userRepo userRepo;
    private moviesController moviesController;
;
    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@RequestBody Users userEntity) {
        Optional<Users> userOptional = userRepo.findByEmail(userEntity.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Tài khoản không tồn tại");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Users user = userOptional.get();

        // Kiểm tra mật khẩu
        if (!user.getPassword().equals(userEntity.getPassword())) {
            response.put("status", "error");
            response.put("message", "Mật khẩu không đúng");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Kiểm tra vai trò
        if (user.getRole() != Users.Role.admin) {
            response.put("status", "error");
            response.put("message", "Chỉ tài khoản admin mới được đăng nhập");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        response.put("status", "success");
        response.put("message", "Đăng nhập thành công");
        return ResponseEntity.ok(response);
    }

    // public void setMoviesController(moviesController moviesController) {
    //     this.moviesController = moviesController;
    // }

    // public moviesController getMoviesController() {
    //     return moviesController;
    // }

}
