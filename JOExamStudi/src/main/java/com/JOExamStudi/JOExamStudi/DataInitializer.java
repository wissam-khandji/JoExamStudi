package com.JOExamStudi.JOExamStudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.entity.Event;
import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;
import com.JOExamStudi.JOExamStudi.repository.EventRepository;
import com.JOExamStudi.JOExamStudi.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Création de l'admin si inexistant et récupération dans une variable
        User admin = userRepository.findByEmail("admin@example.com").orElseGet(() -> {
            User u = new User();
            u.setName("Admin");
            u.setLastname("User");
            u.setEmail("admin@example.com");
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setAccountKey("adminKey123");
            u.setRole("ADMIN");
            return userRepository.save(u);
        });
        
        // Création d'un utilisateur classique si inexistant et récupération dans une variable
        User regularUser = userRepository.findByEmail("user@example.com").orElseGet(() -> {
            User u = new User();
            u.setName("Regular");
            u.setLastname("User");
            u.setEmail("user@example.com");
            u.setPassword(passwordEncoder.encode("user123"));
            u.setAccountKey("userKey123");
            u.setRole("USER");
            return userRepository.save(u);
        });
        
        // Création d'un événement factice
        Event event = new Event();
        event.setTitle("Jeux Olympiques 2024");
        event.setPlacesNb(10000);
        event.setLieu("Paris");
        event.setDescription("Jeux Olympiques en France");
        event.setTime("18:00");
        event.setDate("2024-07-26");
        eventRepository.save(event);
        
        // Création d'un panier de test associé à l'utilisateur régulier, si aucun panier n'existe déjà
        if(cartRepository.findAll().isEmpty()){
            Cart cart = new Cart();
            cart.setUser(regularUser); // Utilise l'utilisateur régulier
            cart.setQuantity(1);
            cart.setTotalPrice(5000);
            cart.setStatus("NEW");
            cartRepository.save(cart);
        }
    }
}
