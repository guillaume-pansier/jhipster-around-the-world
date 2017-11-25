package com.panroets.aroundtheworld.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.panroets.aroundtheworld.service.InterestPointService;
import com.panroets.aroundtheworld.web.rest.errors.BadRequestAlertException;
import com.panroets.aroundtheworld.web.rest.util.HeaderUtil;
import com.panroets.aroundtheworld.service.dto.InterestPointDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InterestPoint.
 */
@RestController
@RequestMapping("/api")
public class InterestPointResource {

    private final Logger log = LoggerFactory.getLogger(InterestPointResource.class);

    private static final String ENTITY_NAME = "interestPoint";

    private final InterestPointService interestPointService;

    public InterestPointResource(InterestPointService interestPointService) {
        this.interestPointService = interestPointService;
    }

    /**
     * POST  /interest-points : Create a new interestPoint.
     *
     * @param interestPointDTO the interestPointDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new interestPointDTO, or with status 400 (Bad Request) if the interestPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/interest-points")
    @Timed
    public ResponseEntity<InterestPointDTO> createInterestPoint(@RequestBody InterestPointDTO interestPointDTO) throws URISyntaxException {
        log.debug("REST request to save InterestPoint : {}", interestPointDTO);
        if (interestPointDTO.getId() != null) {
            throw new BadRequestAlertException("A new interestPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterestPointDTO result = interestPointService.save(interestPointDTO);
        return ResponseEntity.created(new URI("/api/interest-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /interest-points : Updates an existing interestPoint.
     *
     * @param interestPointDTO the interestPointDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated interestPointDTO,
     * or with status 400 (Bad Request) if the interestPointDTO is not valid,
     * or with status 500 (Internal Server Error) if the interestPointDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/interest-points")
    @Timed
    public ResponseEntity<InterestPointDTO> updateInterestPoint(@RequestBody InterestPointDTO interestPointDTO) throws URISyntaxException {
        log.debug("REST request to update InterestPoint : {}", interestPointDTO);
        if (interestPointDTO.getId() == null) {
            return createInterestPoint(interestPointDTO);
        }
        InterestPointDTO result = interestPointService.save(interestPointDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, interestPointDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /interest-points : get all the interestPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of interestPoints in body
     */
    @GetMapping("/interest-points")
    @Timed
    public List<InterestPointDTO> getAllInterestPoints() {
        log.debug("REST request to get all InterestPoints");
        return interestPointService.findAll();
        }

    /**
     * GET  /interest-points/:id : get the "id" interestPoint.
     *
     * @param id the id of the interestPointDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interestPointDTO, or with status 404 (Not Found)
     */
    @GetMapping("/interest-points/{id}")
    @Timed
    public ResponseEntity<InterestPointDTO> getInterestPoint(@PathVariable String id) {
        log.debug("REST request to get InterestPoint : {}", id);
        InterestPointDTO interestPointDTO = interestPointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(interestPointDTO));
    }

    /**
     * DELETE  /interest-points/:id : delete the "id" interestPoint.
     *
     * @param id the id of the interestPointDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/interest-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteInterestPoint(@PathVariable String id) {
        log.debug("REST request to delete InterestPoint : {}", id);
        interestPointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
