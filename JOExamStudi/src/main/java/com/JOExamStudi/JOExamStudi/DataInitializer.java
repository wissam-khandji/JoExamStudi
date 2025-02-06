package com.JOExamStudi.JOExamStudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.entity.Event;
import com.JOExamStudi.JOExamStudi.entity.Offer;
import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;
import com.JOExamStudi.JOExamStudi.repository.EventRepository;
import com.JOExamStudi.JOExamStudi.repository.OfferRepository;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
import com.JOExamStudi.JOExamStudi.repository.UserRepository;

@Component
@Profile("!test")  // S'exécute uniquement lorsque le profil actif n'est PAS "test"
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private OfferRepository offerRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Création de l'admin si inexistant
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
        
        // Création d'un utilisateur classique si inexistant
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
        
        // Création d'une offre factice
        Offer offer = new Offer();
        offer.setOfferType("Solo");
        offer.setDescription("Offre solo pour un participant");
        offer.setPrice(50.0);
        offer.setNbPersonnes(1);
        offerRepository.save(offer);
        
        // Création d'un panier de test associé à l'utilisateur régulier, si aucun panier n'existe déjà
        Cart cart;
        if(cartRepository.findAll().isEmpty()){
            cart = new Cart();
            cart.setUser(regularUser); // Utilise l'utilisateur régulier
            cart.setOffer(offer);      // Associe l'offre au panier
            cart.setQuantity(1);
            cart.setTotalPrice(50.0);
            cart.setStatus("NEW");
            cart = cartRepository.save(cart);
        } else {
            cart = cartRepository.findAll().get(0);
        }
        
        // Création d'un ticket de test associé à ce panier, si aucun ticket n'existe déjà
        if(ticketRepository.findAll().isEmpty()){
            Ticket ticket = new Ticket();
            ticket.setCart(cart);
            // On simule la génération de la paymentKey
            String paymentKey = "samplePaymentKey";
            ticket.setTicketKey(paymentKey);
            // Concaténation de l'accountKey et de la paymentKey pour obtenir la secuKey
            ticket.setSecuKey(cart.getUser().getAccountKey() + paymentKey);
            // Pour le QR code, vous pouvez utiliser une chaîne Base64 simulée ou générer un QR code réel
            // Ici, nous utilisons une chaîne Base64 d'exemple (vous devrez utiliser QrCodeGeneratorService dans un vrai cas)
            ticket.setQrCode("iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQAAAACFI5MzAAABd0lEQVR4Xu2XQW6EMAxFjWaRJUfITeB..."
                    + ""); // Assurez-vous d'avoir une chaîne Base64 complète ici.
            ticket.setStatus("VALID");
            ticketRepository.save(ticket);
        }
    }
}
