package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.UserRepository;
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

@WebMvcTest(UserController.class)
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setIdUser(1L);
        user1.setEmail("admin@example.com");
        user1.setName("Admin");
        
        User user2 = new User();
        user2.setIdUser(2L);
        user2.setEmail("user@example.com");
        user2.setName("Regular");
        
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@example.com"))
                .andExpect(jsonPath("$[1].email").value("user@example.com"));
    }
    
    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setIdUser(1L);
        user.setEmail("newuser@example.com");
        user.setName("New");
        
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        
        String json = "{\"name\":\"New\", \"lastname\":\"User\", \"email\":\"newuser@example.com\", \"password\":\"pass\", \"accountKey\":\"key\", \"role\":\"USER\"}";
        
        mockMvc.perform(post("/api/users")
        		.with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }
}

