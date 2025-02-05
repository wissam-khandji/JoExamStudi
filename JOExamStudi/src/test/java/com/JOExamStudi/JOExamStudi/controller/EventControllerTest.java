package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.Event;
import com.JOExamStudi.JOExamStudi.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EventController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EventRepository eventRepository;
    
    @Test
    public void testGetAllEvents() throws Exception {
        Event event1 = new Event();
        event1.setIdEvent(1L);
        event1.setTitle("Event 1");
        Event event2 = new Event();
        event2.setIdEvent(2L);
        event2.setTitle("Event 2");
        
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));
        
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Event 1"))
                .andExpect(jsonPath("$[1].title").value("Event 2"));
    }
    
    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setIdEvent(1L);
        event.setTitle("New Event");
        
        when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);
        
        String json = "{\"title\":\"New Event\", \"placesNb\":100, \"lieu\":\"Paris\", \"description\":\"Test event\", \"time\":\"18:00\", \"date\":\"2024-07-26\"}";
        
        mockMvc.perform(post("/api/events")
        		.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Event"));
    }
}

