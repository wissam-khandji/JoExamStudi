package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.dto.OfferSalesDTO;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    public void testGetSalesByOffer() throws Exception {
        // Prépare une réponse de test pour l'agrégation des ventes
        OfferSalesDTO dto = new OfferSalesDTO(1L, "Solo", 1L);
        when(ticketRepository.findSalesByOffer()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/admin/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].offerId").value(1))
                .andExpect(jsonPath("$[0].offerType").value("Solo"))
                .andExpect(jsonPath("$[0].salesCount").value(1));
    }
}

