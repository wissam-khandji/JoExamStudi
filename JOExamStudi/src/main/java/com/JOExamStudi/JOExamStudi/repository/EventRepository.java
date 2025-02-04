package com.JOExamStudi.JOExamStudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JOExamStudi.JOExamStudi.entity.Event;


public interface EventRepository extends JpaRepository<Event, Long> {
}
