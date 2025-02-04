package com.JOExamStudi.JOExamStudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    
    @GetMapping
    public List<Ticket> getAllTickets() {
       return ticketRepository.findAll();
    }
    
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
       return ticketRepository.save(ticket);
    }
}
