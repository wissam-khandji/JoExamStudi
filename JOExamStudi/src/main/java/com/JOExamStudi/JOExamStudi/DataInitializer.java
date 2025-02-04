package com.JOExamStudi.JOExamStudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.JOExamStudi.JOExamStudi.entity.Event;
import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.EventRepository;
import com.JOExamStudi.JOExamStudi.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Création de l'admin si inexistant
        if(userRepository.findByEmail("admin@example.com").isEmpty()){
            User admin = new User();
            admin.setName("Admin");
            admin.setLastname("User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAccountKey("adminKey123");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        
        // Création d'un utilisateur classique si inexistant
        if(userRepository.findByEmail("user@example.com").isEmpty()){
            User user = new User();
            user.setName("Regular");
            user.setLastname("User");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setAccountKey("userKey123");
            user.setRole("USER");
            userRepository.save(user);
        }
        
        // Création d'un événement factice
        Event event = new Event();
        event.setTitle("Jeux Olympiques 2024");
        event.setPlacesNb(10000);
        event.setLieu("Paris");
        event.setDescription("Jeux Olympiques en France");
        event.setTime("18:00");
        event.setDate("2024-07-26");
        eventRepository.save(event);
    }
}

