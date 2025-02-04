package com.JOExamStudi.JOExamStudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JOExamStudi.JOExamStudi.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

