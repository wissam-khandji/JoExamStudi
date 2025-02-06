package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.security.CustomUserDetailsService;
import com.JOExamStudi.JOExamStudi.security.SecurityConfig;
import com.JOExamStudi.JOExamStudi.service.PaymentService;
import com.JOExamStudi.JOExamStudi.service.TicketService;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(SecurityConfig.class)  // Import de la configuration de sécurité personnalisée
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
@ActiveProfiles("test")
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private TicketService ticketService;

    // Bean pour satisfaire la dépendance de SecurityConfig
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testFinalizePaymentAndTicket() throws Exception {
        // Création d'un PaymentIntent fictif via mock
        PaymentIntent paymentIntent = Mockito.mock(PaymentIntent.class);
        // On simule la réponse JSON du PaymentIntent
        when(paymentIntent.toJson()).thenReturn("{\"id\": \"pi_test\", \"status\": \"requires_payment_method\"}");

        // Simuler le comportement de PaymentService et TicketService
        when(paymentService.createPaymentIntent(anyLong(), anyString())).thenReturn(paymentIntent);
        
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1L);
        ticket.setStatus("VALID");
        when(ticketService.finalizeTicket(anyLong())).thenReturn(ticket);

        mockMvc.perform(post("/api/payment/finalize")
                .param("amount", "5000")
                .param("currency", "eur")
                .param("cartId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket.idTicket").value(1))
                .andExpect(jsonPath("$.ticket.status").value("VALID"))
                .andExpect(jsonPath("$.paymentIntent").exists());
    }
}
