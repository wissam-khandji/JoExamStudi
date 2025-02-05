package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.Offer;
import com.JOExamStudi.JOExamStudi.repository.OfferRepository;
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

@WebMvcTest(OfferController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OfferRepository offerRepository;
    
    @Test
    public void testGetAllOffers() throws Exception {
        Offer offer1 = new Offer();
        offer1.setIdOffer(1L);
        offer1.setOfferType("Solo");
        
        Offer offer2 = new Offer();
        offer2.setIdOffer(2L);
        offer2.setOfferType("Familiale");
        
        when(offerRepository.findAll()).thenReturn(Arrays.asList(offer1, offer2));
        
        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].offerType").value("Solo"))
                .andExpect(jsonPath("$[1].offerType").value("Familiale"));
    }
    
    @Test
    public void testCreateOffer() throws Exception {
        Offer offer = new Offer();
        offer.setIdOffer(1L);
        offer.setOfferType("Duo");
        
        when(offerRepository.save(Mockito.any(Offer.class))).thenReturn(offer);
        
        String json = "{\"offerType\":\"Duo\", \"description\":\"Test offer\", \"price\":50.0, \"nbPersonnes\":2}";
        
        mockMvc.perform(post("/api/offers")
        		.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offerType").value("Duo"));
    }
}

