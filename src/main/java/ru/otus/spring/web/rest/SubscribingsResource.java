package ru.otus.spring.web.rest;

import ru.otus.spring.domain.Subscribings;
import ru.otus.spring.repository.SubscribingsRepository;
import ru.otus.spring.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.otus.spring.domain.Subscribings}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubscribingsResource {

    private final Logger log = LoggerFactory.getLogger(SubscribingsResource.class);

    private static final String ENTITY_NAME = "subscribings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscribingsRepository subscribingsRepository;

    public SubscribingsResource(SubscribingsRepository subscribingsRepository) {
        this.subscribingsRepository = subscribingsRepository;
    }

    /**
     * {@code POST  /subscribings} : Create a new subscribings.
     *
     * @param subscribings the subscribings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscribings, or with status {@code 400 (Bad Request)} if the subscribings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscribings")
    public ResponseEntity<Subscribings> createSubscribings(@Valid @RequestBody Subscribings subscribings) throws URISyntaxException {
        log.debug("REST request to save Subscribings : {}", subscribings);
        if (subscribings.getId() != null) {
            throw new BadRequestAlertException("A new subscribings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subscribings result = subscribingsRepository.save(subscribings);
        return ResponseEntity.created(new URI("/api/subscribings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscribings} : Updates an existing subscribings.
     *
     * @param subscribings the subscribings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscribings,
     * or with status {@code 400 (Bad Request)} if the subscribings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscribings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscribings")
    public ResponseEntity<Subscribings> updateSubscribings(@Valid @RequestBody Subscribings subscribings) throws URISyntaxException {
        log.debug("REST request to update Subscribings : {}", subscribings);
        if (subscribings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subscribings result = subscribingsRepository.save(subscribings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscribings.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subscribings} : get all the subscribings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscribings in body.
     */
    @GetMapping("/subscribings")
    public List<Subscribings> getAllSubscribings() {
        log.debug("REST request to get all Subscribings");
        return subscribingsRepository.findAll();
    }

    /**
     * {@code GET  /subscribings/:id} : get the "id" subscribings.
     *
     * @param id the id of the subscribings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscribings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscribings/{id}")
    public ResponseEntity<Subscribings> getSubscribings(@PathVariable Long id) {
        log.debug("REST request to get Subscribings : {}", id);
        Optional<Subscribings> subscribings = subscribingsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subscribings);
    }

    /**
     * {@code DELETE  /subscribings/:id} : delete the "id" subscribings.
     *
     * @param id the id of the subscribings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscribings/{id}")
    public ResponseEntity<Void> deleteSubscribings(@PathVariable Long id) {
        log.debug("REST request to delete Subscribings : {}", id);
        subscribingsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
