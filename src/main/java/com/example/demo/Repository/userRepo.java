package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Users;

public interface userRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    
} 
