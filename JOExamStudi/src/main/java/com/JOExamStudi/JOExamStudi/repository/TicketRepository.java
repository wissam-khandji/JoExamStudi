package com.JOExamStudi.JOExamStudi.repository;

import com.JOExamStudi.JOExamStudi.entity.Ticket;
import com.JOExamStudi.JOExamStudi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT new com.JOExamStudi.JOExamStudi.dto.OfferSalesDTO(o.idOffer, o.offerType, COUNT(t)) " +
           "FROM Ticket t JOIN t.cart.offer o " +
           "GROUP BY o.idOffer, o.offerType")
    List<com.JOExamStudi.JOExamStudi.dto.OfferSalesDTO> findSalesByOffer();

    // Ajoutez cette méthode pour retrouver le ticket associé à un panier
    Optional<Ticket> findByCart(Cart cart);
}
