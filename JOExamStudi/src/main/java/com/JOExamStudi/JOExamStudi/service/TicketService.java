package com.JOExamStudi.JOExamStudi.service;

import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.entity.Cart;
import com.JOExamStudi.JOExamStudi.entity.User;
import com.JOExamStudi.JOExamStudi.repository.CartRepository;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private QrCodeGeneratorService qrCodeGeneratorService;

    // Génère une clé aléatoire pour le paiement
    private String generatePaymentKey() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32); // Chaîne alphanumérique aléatoire
    }

    /**
     * Finalise la création du billet :
     * - Récupère le panier et l'utilisateur associé pour obtenir accountKey.
     * - Vérifie si un ticket existe déjà pour ce panier. Si oui, on le met à jour ; sinon, on crée un nouveau ticket.
     * - Génère une paymentKey.
     * - Concatène accountKey et paymentKey pour obtenir la clé finale (secuKey).
     * - Génère un QR code à partir de la clé finale et l'encode en Base64.
     * - Sauvegarde (ou met à jour) le Ticket.
     *
     * @param cartId l'identifiant du panier.
     * @return le Ticket finalisé.
     * @throws Exception si le panier n'est pas trouvé ou en cas d'erreur de génération.
     */
    public Ticket finalizeTicket(Long cartId) throws Exception {
        // Récupère le panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Panier introuvable"));

        // Récupère l'utilisateur associé
        User user = cart.getUser();
        if (user == null) {
            throw new Exception("Le panier n'a pas d'utilisateur associé");
        }
        String accountKey = user.getAccountKey();

        // Génération de la clé de paiement
        String paymentKey = generatePaymentKey();

        // Concaténation pour obtenir la clé finale
        String finalKey = accountKey + paymentKey;

        // Génération du QR code (image PNG encodée en Base64)
        byte[] qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(finalKey, 200, 200);
        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);

        // Vérifie si un ticket existe déjà pour ce panier
        Optional<Ticket> optionalTicket = ticketRepository.findByCart(cart);
        Ticket ticket;
        if (optionalTicket.isPresent()) {
            ticket = optionalTicket.get();
            // Mise à jour du ticket existant
            ticket.setTicketKey(paymentKey);
            ticket.setSecuKey(finalKey);
            ticket.setQrCode(qrCodeBase64);
            ticket.setStatus("VALID");
        } else {
            // Création d'un nouveau ticket
            ticket = new Ticket();
            ticket.setCart(cart);
            ticket.setTicketKey(paymentKey);
            ticket.setSecuKey(finalKey);
            ticket.setQrCode(qrCodeBase64);
            ticket.setStatus("VALID");
        }

        return ticketRepository.save(ticket);
    }
}
