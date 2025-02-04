package com.JOExamStudi.JOExamStudi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String title;
    private int placesNb;
    private String lieu;
    private String description;
    private String time;
    private String date;

    // Getters & Setters
    public Long getIdEvent() {
        return idEvent;
    }
    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPlacesNb() {
        return placesNb;
    }
    public void setPlacesNb(int placesNb) {
        this.placesNb = placesNb;
    }
    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
