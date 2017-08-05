package kz.izzi.bus.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.izzi.bus.domain.LotteryRound;

import kz.izzi.bus.repository.LotteryRoundRepository;
import kz.izzi.bus.web.rest.util.HeaderUtil;
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
 * REST controller for managing LotteryRound.
 */
@RestController
@RequestMapping("/api")
public class LotteryRoundResource {

    private final Logger log = LoggerFactory.getLogger(LotteryRoundResource.class);

    private static final String ENTITY_NAME = "lotteryRound";

    private final LotteryRoundRepository lotteryRoundRepository;

    public LotteryRoundResource(LotteryRoundRepository lotteryRoundRepository) {
        this.lotteryRoundRepository = lotteryRoundRepository;
    }

    /**
     * POST  /lottery-rounds : Create a new lotteryRound.
     *
     * @param lotteryRound the lotteryRound to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lotteryRound, or with status 400 (Bad Request) if the lotteryRound has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lottery-rounds")
    @Timed
    public ResponseEntity<LotteryRound> createLotteryRound(@Valid @RequestBody LotteryRound lotteryRound) throws URISyntaxException {
        log.debug("REST request to save LotteryRound : {}", lotteryRound);
        if (lotteryRound.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lotteryRound cannot already have an ID")).body(null);
        }
        LotteryRound result = lotteryRoundRepository.save(lotteryRound);
        return ResponseEntity.created(new URI("/api/lottery-rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lottery-rounds : Updates an existing lotteryRound.
     *
     * @param lotteryRound the lotteryRound to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lotteryRound,
     * or with status 400 (Bad Request) if the lotteryRound is not valid,
     * or with status 500 (Internal Server Error) if the lotteryRound couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lottery-rounds")
    @Timed
    public ResponseEntity<LotteryRound> updateLotteryRound(@Valid @RequestBody LotteryRound lotteryRound) throws URISyntaxException {
        log.debug("REST request to update LotteryRound : {}", lotteryRound);
        if (lotteryRound.getId() == null) {
            return createLotteryRound(lotteryRound);
        }
        LotteryRound result = lotteryRoundRepository.save(lotteryRound);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lotteryRound.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lottery-rounds : get all the lotteryRounds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lotteryRounds in body
     */
    @GetMapping("/lottery-rounds")
    @Timed
    public List<LotteryRound> getAllLotteryRounds() {
        log.debug("REST request to get all LotteryRounds");
        return lotteryRoundRepository.findAll();
    }

    /**
     * GET  /lottery-rounds/:id : get the "id" lotteryRound.
     *
     * @param id the id of the lotteryRound to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lotteryRound, or with status 404 (Not Found)
     */
    @GetMapping("/lottery-rounds/{id}")
    @Timed
    public ResponseEntity<LotteryRound> getLotteryRound(@PathVariable Long id) {
        log.debug("REST request to get LotteryRound : {}", id);
        LotteryRound lotteryRound = lotteryRoundRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lotteryRound));
    }

    /**
     * DELETE  /lottery-rounds/:id : delete the "id" lotteryRound.
     *
     * @param id the id of the lotteryRound to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lottery-rounds/{id}")
    @Timed
    public ResponseEntity<Void> deleteLotteryRound(@PathVariable Long id) {
        log.debug("REST request to delete LotteryRound : {}", id);
        lotteryRoundRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
