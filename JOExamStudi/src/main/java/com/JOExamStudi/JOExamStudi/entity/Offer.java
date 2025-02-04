package com.JOExamStudi.JOExamStudi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffer;
    private String offerType;
    private String description;
    private double price;
    private int nbPersonnes;

    @ManyToOne
    @JoinColumn(name = "idEvent")
    private Event event;

    // Getters & Setters
    public Long getIdOffer() {
        return idOffer;
    }
    public void setIdOffer(Long idOffer) {
        this.idOffer = idOffer;
    }
    public String getOfferType() {
        return offerType;
    }
    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getNbPersonnes() {
        return nbPersonnes;
    }
    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
}

