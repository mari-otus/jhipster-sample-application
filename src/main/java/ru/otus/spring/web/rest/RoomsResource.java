package ru.otus.spring.web.rest;

import ru.otus.spring.domain.Rooms;
import ru.otus.spring.repository.RoomsRepository;
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
 * REST controller for managing {@link ru.otus.spring.domain.Rooms}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoomsResource {

    private final Logger log = LoggerFactory.getLogger(RoomsResource.class);

    private static final String ENTITY_NAME = "rooms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomsRepository roomsRepository;

    public RoomsResource(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    /**
     * {@code POST  /rooms} : Create a new rooms.
     *
     * @param rooms the rooms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rooms, or with status {@code 400 (Bad Request)} if the rooms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rooms")
    public ResponseEntity<Rooms> createRooms(@Valid @RequestBody Rooms rooms) throws URISyntaxException {
        log.debug("REST request to save Rooms : {}", rooms);
        if (rooms.getId() != null) {
            throw new BadRequestAlertException("A new rooms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rooms result = roomsRepository.save(rooms);
        return ResponseEntity.created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rooms} : Updates an existing rooms.
     *
     * @param rooms the rooms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rooms,
     * or with status {@code 400 (Bad Request)} if the rooms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rooms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rooms")
    public ResponseEntity<Rooms> updateRooms(@Valid @RequestBody Rooms rooms) throws URISyntaxException {
        log.debug("REST request to update Rooms : {}", rooms);
        if (rooms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rooms result = roomsRepository.save(rooms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rooms.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rooms} : get all the rooms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rooms in body.
     */
    @GetMapping("/rooms")
    public List<Rooms> getAllRooms() {
        log.debug("REST request to get all Rooms");
        return roomsRepository.findAll();
    }

    /**
     * {@code GET  /rooms/:id} : get the "id" rooms.
     *
     * @param id the id of the rooms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rooms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rooms/{id}")
    public ResponseEntity<Rooms> getRooms(@PathVariable Long id) {
        log.debug("REST request to get Rooms : {}", id);
        Optional<Rooms> rooms = roomsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rooms);
    }

    /**
     * {@code DELETE  /rooms/:id} : delete the "id" rooms.
     *
     * @param id the id of the rooms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRooms(@PathVariable Long id) {
        log.debug("REST request to delete Rooms : {}", id);
        roomsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
