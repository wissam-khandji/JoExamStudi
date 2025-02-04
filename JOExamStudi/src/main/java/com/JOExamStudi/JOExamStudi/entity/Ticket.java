package com.JOExamStudi.JOExamStudi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @OneToOne
    @JoinColumn(name = "idCart")
    private Cart cart;

    private String ticketKey;
    private String secuKey;
    private String qrCode;
    private String status;

    // Getters & Setters
    public Long getIdTicket() {
        return idTicket;
    }
    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public String getTicketKey() {
        return ticketKey;
    }
    public void setTicketKey(String ticketKey) {
        this.ticketKey = ticketKey;
    }
    public String getSecuKey() {
        return secuKey;
    }
    public void setSecuKey(String secuKey) {
        this.secuKey = secuKey;
    }
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
