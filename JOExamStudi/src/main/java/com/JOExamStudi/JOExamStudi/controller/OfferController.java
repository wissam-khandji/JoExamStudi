package com.JOExamStudi.JOExamStudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.JOExamStudi.JOExamStudi.entity.Offer;
import com.JOExamStudi.JOExamStudi.repository.OfferRepository;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;
    
    @GetMapping
    public List<Offer> getAllOffers() {
       return offerRepository.findAll();
    }
    
    @PostMapping
    public Offer createOffer(@RequestBody Offer offer) {
       return offerRepository.save(offer);
    }
}
