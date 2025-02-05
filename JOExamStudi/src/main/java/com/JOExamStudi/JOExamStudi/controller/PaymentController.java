package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.service.PaymentService;
import com.JOExamStudi.JOExamStudi.service.TicketService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TicketService ticketService;

    /**
     * Simule le paiement et finalise le billet.
     * Exemples d'appel : POST /api/payment/finalize?amount=5000&currency=eur&cartId=1
     *
     * @param amount   montant en centimes (ex: 5000 pour 50,00 €)
     * @param currency devise (ex: "eur")
     * @param cartId   identifiant du panier
     * @return un JSON contenant le PaymentIntent et le Ticket créé
     */
    @PostMapping("/finalize")
    public ResponseEntity<?> finalizePaymentAndTicket(@RequestParam("amount") long amount,
                                                      @RequestParam("currency") String currency,
                                                      @RequestParam("cartId") Long cartId) {
        try {
            // Crée le PaymentIntent via Stripe (simulation de paiement)
            PaymentIntent intent = paymentService.createPaymentIntent(amount, currency);
            // Finalise le billet en générant la clé finale et le QR code
            Ticket ticket = ticketService.finalizeTicket(cartId);

            Map<String, Object> response = new HashMap<>();
            response.put("paymentIntent", intent.toJson());
            response.put("ticket", ticket);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur Stripe : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la finalisation du paiement : " + e.getMessage());
        }
    }
}
