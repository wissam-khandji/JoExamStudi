package com.JOExamStudi.JOExamStudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JOExamStudi.JOExamStudi.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
