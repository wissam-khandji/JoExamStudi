package com.JOExamStudi.JOExamStudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.JOExamStudi.JOExamStudi.entity.Event;
import com.JOExamStudi.JOExamStudi.repository.EventRepository;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    
    @GetMapping
    public List<Event> getAllEvents() {
       return eventRepository.findAll();
    }
    
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
       return eventRepository.save(event);
    }
}

