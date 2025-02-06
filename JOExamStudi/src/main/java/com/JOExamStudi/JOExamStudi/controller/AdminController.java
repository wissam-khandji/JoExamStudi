package com.JOExamStudi.JOExamStudi.controller;

import com.JOExamStudi.JOExamStudi.dto.OfferSalesDTO;
import com.JOExamStudi.JOExamStudi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Endpoint permettant à l'administrateur de visualiser le nombre de ventes par offre.
     * Exemple d'appel : GET /api/admin/sales
     *
     * @return une liste d'OfferSalesDTO contenant l'id, le type d'offre et le nombre de ventes.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales")
    public List<OfferSalesDTO> getSalesByOffer() {
        return ticketRepository.findSalesByOffer();
    }
}
