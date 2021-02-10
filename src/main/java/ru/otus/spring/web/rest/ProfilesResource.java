package ru.otus.spring.web.rest;

import ru.otus.spring.domain.Profiles;
import ru.otus.spring.repository.ProfilesRepository;
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
 * REST controller for managing {@link ru.otus.spring.domain.Profiles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfilesResource {

    private final Logger log = LoggerFactory.getLogger(ProfilesResource.class);

    private static final String ENTITY_NAME = "profiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfilesRepository profilesRepository;

    public ProfilesResource(ProfilesRepository profilesRepository) {
        this.profilesRepository = profilesRepository;
    }

    /**
     * {@code POST  /profiles} : Create a new profiles.
     *
     * @param profiles the profiles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profiles, or with status {@code 400 (Bad Request)} if the profiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profiles")
    public ResponseEntity<Profiles> createProfiles(@Valid @RequestBody Profiles profiles) throws URISyntaxException {
        log.debug("REST request to save Profiles : {}", profiles);
        if (profiles.getId() != null) {
            throw new BadRequestAlertException("A new profiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profiles result = profilesRepository.save(profiles);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profiles} : Updates an existing profiles.
     *
     * @param profiles the profiles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profiles,
     * or with status {@code 400 (Bad Request)} if the profiles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profiles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profiles")
    public ResponseEntity<Profiles> updateProfiles(@Valid @RequestBody Profiles profiles) throws URISyntaxException {
        log.debug("REST request to update Profiles : {}", profiles);
        if (profiles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Profiles result = profilesRepository.save(profiles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profiles.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public List<Profiles> getAllProfiles() {
        log.debug("REST request to get all Profiles");
        return profilesRepository.findAll();
    }

    /**
     * {@code GET  /profiles/:id} : get the "id" profiles.
     *
     * @param id the id of the profiles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profiles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<Profiles> getProfiles(@PathVariable Long id) {
        log.debug("REST request to get Profiles : {}", id);
        Optional<Profiles> profiles = profilesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profiles);
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profiles.
     *
     * @param id the id of the profiles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfiles(@PathVariable Long id) {
        log.debug("REST request to delete Profiles : {}", id);
        profilesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
