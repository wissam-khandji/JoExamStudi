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
        return new BigInteger(130, random).toString(32); // chaine alphanumérique aléatoire
    }

    /**
     * Finalise la création du billet :
     *  - Récupère le panier et l'utilisateur associé pour obtenir accountKey.
     *  - Génère une paymentKey.
     *  - Concatène accountKey et paymentKey pour obtenir la clé finale.
     *  - Génère un QR code (image PNG encodée en Base64) à partir de la clé finale.
     *  - Crée et sauvegarde le Ticket.
     *
     * @param cartId l'identifiant du panier associé à la commande.
     * @return le Ticket créé.
     * @throws Exception en cas d'erreur (panier non trouvé, erreur QR code, etc.).
     */
    public Ticket finalizeTicket(Long cartId) throws Exception {
        // Récupération du panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Panier introuvable"));

        // Récupération de la clé de compte de l'utilisateur
        User user = cart.getUser();
        String accountKey = user.getAccountKey();

        // Génération de la clé de paiement
        String paymentKey = generatePaymentKey();

        // Concaténation pour obtenir la clé finale
        String finalKey = accountKey + paymentKey;

        // Génération du QR code à partir de la clé finale
        byte[] qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(finalKey, 200, 200);
        // Encodage en Base64 pour stockage en String
        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);

        // Création du billet (Ticket)
        Ticket ticket = new Ticket();
        ticket.setCart(cart);
        ticket.setTicketKey(paymentKey);  // clé générée lors du paiement
        ticket.setSecuKey(finalKey);       // clé finale (accountKey + paymentKey)
        ticket.setQrCode(qrCodeBase64);     // QR code encodé en Base64
        ticket.setStatus("VALID");

        return ticketRepository.save(ticket);
    }
}
