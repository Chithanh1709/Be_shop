package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.users;

public interface userRepo extends JpaRepository<users, Integer> {
    Optional<users> findByEmail(String email);

    
} 
