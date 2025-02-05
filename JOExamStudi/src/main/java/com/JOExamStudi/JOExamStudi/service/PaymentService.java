package com.JOExamStudi.JOExamStudi.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @PostConstruct
    public void init() {
        // Initialisation avec la clé secrète Stripe de test
        Stripe.apiKey = "sk_test_51QpBaU2ceSr1DyIFNswIFXx5pkZsNHkW3ySzRjUIAUyA110nAOGikzya9HkZNqpZ1i2JERWUZrVieg6Zv0Y2P33d00YjbckDDE"; //Clef fourni par stripe
    }

    /**
     * Crée un PaymentIntent pour simuler un paiement.
     *
     * @param amount   Le montant en centimes (par exemple 5000 pour 50,00 €).
     * @param currency La devise (ex. "eur" ou "usd").
     * @return Le PaymentIntent créé.
     * @throws StripeException En cas d’erreur avec l’API Stripe.
     */
    public PaymentIntent createPaymentIntent(long amount, String currency) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("payment_method_types", java.util.List.of("card"));
        return PaymentIntent.create(params);
    }
}
