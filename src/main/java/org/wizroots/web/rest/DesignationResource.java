package org.wizroots.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.wizroots.domain.Designation;
import org.wizroots.repository.DesignationRepository;
import org.wizroots.web.rest.errors.BadRequestAlertException;
import org.wizroots.web.rest.util.HeaderUtil;
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
 * REST controller for managing Designation.
 */
@RestController
@RequestMapping("/api")
public class DesignationResource {

    private final Logger log = LoggerFactory.getLogger(DesignationResource.class);

    private static final String ENTITY_NAME = "designation";

    private final DesignationRepository designationRepository;

    public DesignationResource(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * POST  /designations : Create a new designation.
     *
     * @param designation the designation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designation, or with status 400 (Bad Request) if the designation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designations")
    @Timed
    public ResponseEntity<Designation> createDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to save Designation : {}", designation);
        if (designation.getId() != null) {
            throw new BadRequestAlertException("A new designation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Designation result = designationRepository.save(designation);
        return ResponseEntity.created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designations : Updates an existing designation.
     *
     * @param designation the designation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designation,
     * or with status 400 (Bad Request) if the designation is not valid,
     * or with status 500 (Internal Server Error) if the designation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designations")
    @Timed
    public ResponseEntity<Designation> updateDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to update Designation : {}", designation);
        if (designation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Designation result = designationRepository.save(designation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designations : get all the designations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of designations in body
     */
    @GetMapping("/designations")
    @Timed
    public List<Designation> getAllDesignations() {
        log.debug("REST request to get all Designations");
        return designationRepository.findAll();
    }

    /**
     * GET  /designations/:id : get the "id" designation.
     *
     * @param id the id of the designation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designation, or with status 404 (Not Found)
     */
    @GetMapping("/designations/{id}")
    @Timed
    public ResponseEntity<Designation> getDesignation(@PathVariable Long id) {
        log.debug("REST request to get Designation : {}", id);
        Optional<Designation> designation = designationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(designation);
    }

    /**
     * DELETE  /designations/:id : delete the "id" designation.
     *
     * @param id the id of the designation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        log.debug("REST request to delete Designation : {}", id);

        designationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
