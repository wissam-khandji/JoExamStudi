package com.JOExamStudi.JOExamStudi.service;

import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Active le profil "test" afin que DataInitializer ne s'exécute pas
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private QrCodeGeneratorService qrCodeGeneratorService;

    @Test
    public void testFinalizeTicket() throws Exception {
        // Crée un utilisateur de test avec une accountKey
        User user = new User();
        user.setIdUser(1L);
        user.setAccountKey("userKey123");

        // Crée un panier de test associé à l'utilisateur
        Cart cart = new Cart();
        cart.setIdCart(1L);
        cart.setUser(user);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        // Simuler la génération du QR code (renvoie un tableau de bytes)
        when(qrCodeGeneratorService.generateQRCodeImage(any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn("dummyQRCode".getBytes());
        // Simuler la sauvegarde du ticket (attribue un id)
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setIdTicket(1L);
            return t;
        });

        Ticket ticket = ticketService.finalizeTicket(1L);
        assertNotNull(ticket);
        assertEquals("VALID", ticket.getStatus());
        // Vérifier que la secuKey est la concaténation de l'accountKey et de la paymentKey
        assertEquals(user.getAccountKey() + ticket.getTicketKey(), ticket.getSecuKey());
        // Vérifier le QR code encodé en Base64
        String expectedQRCode = java.util.Base64.getEncoder().encodeToString("dummyQRCode".getBytes());
        assertEquals(expectedQRCode, ticket.getQrCode());
    }
}

