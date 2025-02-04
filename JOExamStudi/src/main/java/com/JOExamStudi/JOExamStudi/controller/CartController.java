package com.JOExamStudi.JOExamStudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    
    @GetMapping
    public List<Cart> getAllCarts() {
       return cartRepository.findAll();
    }
    
    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
       return cartRepository.save(cart);
    }
}

