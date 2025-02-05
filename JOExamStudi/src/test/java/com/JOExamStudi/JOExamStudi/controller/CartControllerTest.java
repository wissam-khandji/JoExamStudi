package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;
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

@WebMvcTest(CartController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CartRepository cartRepository;
    
    @Test
    public void testGetAllCarts() throws Exception {
        Cart cart1 = new Cart();
        cart1.setIdCart(1L);
        cart1.setQuantity(2);
        Cart cart2 = new Cart();
        cart2.setIdCart(2L);
        cart2.setQuantity(1);
        
        when(cartRepository.findAll()).thenReturn(Arrays.asList(cart1, cart2));
        
        mockMvc.perform(get("/api/carts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[1].quantity").value(1));
    }
    
    @Test
    public void testCreateCart() throws Exception {
        Cart cart = new Cart();
        cart.setIdCart(1L);
        cart.setQuantity(3);
        
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);
        
        String json = "{\"quantity\":3, \"totalPrice\":150.0, \"status\":\"NEW\"}";
        
        mockMvc.perform(post("/api/carts")
        		.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3));
    }
}

