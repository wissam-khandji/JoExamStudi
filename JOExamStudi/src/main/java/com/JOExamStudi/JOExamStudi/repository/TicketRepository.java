package com.JOExamStudi.JOExamStudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JOExamStudi.JOExamStudi.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}


