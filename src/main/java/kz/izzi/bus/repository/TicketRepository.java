package kz.izzi.bus.repository;

import kz.izzi.bus.domain.Ticket;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Ticket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Ticket findByTicketId(String ticketId);
    List<Ticket> findByValid(Boolean valid);
}
