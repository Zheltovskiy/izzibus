package kz.izzi.bus.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.izzi.bus.domain.Ticket;

import kz.izzi.bus.repository.TicketRepository;
import kz.izzi.bus.web.rest.util.HeaderUtil;
import kz.izzi.bus.service.dto.TicketDTO;
import kz.izzi.bus.service.mapper.TicketMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketResource(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    /**
     * POST  /tickets : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketDTO, or with status 400 (Bad Request) if the ticket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ticket cannot already have an ID")).body(null);
        }
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO result = ticketMapper.toDto(ticket);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tickets : Updates an existing ticket.
     *
     * @param ticketDTO the ticketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketDTO,
     * or with status 400 (Bad Request) if the ticketDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to update Ticket : {}", ticketDTO);
        if (ticketDTO.getId() == null) {
            return createTicket(ticketDTO);
        }
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO result = ticketMapper.toDto(ticket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickets : get all the tickets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tickets in body
     */
    @GetMapping("/tickets")
    @Timed
    public List<TicketDTO> getAllTickets() {
        log.debug("REST request to get all Tickets");
        List<Ticket> tickets = ticketRepository.findAll();
        return ticketMapper.toDto(tickets);
    }

    /**
     * GET  /tickets/:id : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        Ticket ticket = ticketRepository.findOne(id);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketDTO));
    }

    /**
     * DELETE  /tickets/:id : delete the "id" ticket.
     *
     * @param id the id of the ticketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
