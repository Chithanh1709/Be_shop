package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.users;
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
public class userController {
    @Autowired
    private userRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody users entity) {
        Optional<users> userOptional = userRepo.findByEmail(entity.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Tài khoản không tồn tại");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        users user = userOptional.get();

        // Kiểm tra mật khẩu
        if (!user.getPassword().equals(entity.getPassword())) {
            response.put("status", "error");
            response.put("message", "Mật khẩu không đúng");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Kiểm tra vai trò
        if (user.getRole() != users.Role.customer) {
            response.put("status", "error");
            response.put("message", "Chỉ tài khoản khách hàng mới được đăng nhập");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        response.put("status", "success");
        response.put("message", "Đăng nhập thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> register(@RequestBody users entity) {
        Optional<users> existingUser = userRepo.findByEmail(entity.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (existingUser.isPresent()) {
            response.put("status", "error");
            response.put("message", "Tài khoản đã tồn tại");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Gán role mặc định là "customer" nếu chưa có
        if (entity.getRole() == null) {
            entity.setRole(users.Role.customer);
        }

        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(java.time.LocalDateTime.now());
        }

        userRepo.save(entity);
        response.put("status", "success");
        response.put("message", "Đăng ký thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recover")
    public ResponseEntity<?> recover(@RequestBody users entity) {
        Optional<users> userOptional = userRepo.findByEmail(entity.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Tài khoản không tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        users user = userOptional.get();
        user.setPassword(entity.getPassword());
        userRepo.save(user);

        response.put("status", "success");
        response.put("message", "Đặt lại mật khẩu thành công");
        return ResponseEntity.ok(response);
    }

}
