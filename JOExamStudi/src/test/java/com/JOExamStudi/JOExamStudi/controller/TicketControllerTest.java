package com.JOExamStudi.JOExamStudi.controller;


import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
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

@WebMvcTest(TicketController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TicketRepository ticketRepository;
    
    @Test
    public void testGetAllTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setIdTicket(1L);
        ticket1.setStatus("ACTIVE");
        Ticket ticket2 = new Ticket();
        ticket2.setIdTicket(2L);
        ticket2.setStatus("USED");
        
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));
        
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].status").value("USED"));
    }
    
    @Test
    public void testCreateTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1L);
        ticket.setStatus("ACTIVE");
        
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
        
        String json = "{\"ticketKey\":\"key\",\"secuKey\":\"secu\",\"qrCode\":\"code\",\"status\":\"ACTIVE\"}";
        
        mockMvc.perform(post("/api/tickets")
        		.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
