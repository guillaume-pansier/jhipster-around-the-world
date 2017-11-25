package com.panroets.aroundtheworld.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.panroets.aroundtheworld.domain.CountryPath;

import com.panroets.aroundtheworld.repository.CountryPathRepository;
import com.panroets.aroundtheworld.web.rest.errors.BadRequestAlertException;
import com.panroets.aroundtheworld.web.rest.util.HeaderUtil;
import com.panroets.aroundtheworld.service.dto.CountryPathDTO;
import com.panroets.aroundtheworld.service.mapper.CountryPathMapper;
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
 * REST controller for managing CountryPath.
 */
@RestController
@RequestMapping("/api")
public class CountryPathResource {

    private final Logger log = LoggerFactory.getLogger(CountryPathResource.class);

    private static final String ENTITY_NAME = "countryPath";

    private final CountryPathRepository countryPathRepository;

    private final CountryPathMapper countryPathMapper;

    public CountryPathResource(CountryPathRepository countryPathRepository, CountryPathMapper countryPathMapper) {
        this.countryPathRepository = countryPathRepository;
        this.countryPathMapper = countryPathMapper;
    }

    /**
     * POST  /country-paths : Create a new countryPath.
     *
     * @param countryPathDTO the countryPathDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countryPathDTO, or with status 400 (Bad Request) if the countryPath has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/country-paths")
    @Timed
    public ResponseEntity<CountryPathDTO> createCountryPath(@RequestBody CountryPathDTO countryPathDTO) throws URISyntaxException {
        log.debug("REST request to save CountryPath : {}", countryPathDTO);
        if (countryPathDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryPath cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryPath countryPath = countryPathMapper.toEntity(countryPathDTO);
        countryPath = countryPathRepository.save(countryPath);
        CountryPathDTO result = countryPathMapper.toDto(countryPath);
        return ResponseEntity.created(new URI("/api/country-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /country-paths : Updates an existing countryPath.
     *
     * @param countryPathDTO the countryPathDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countryPathDTO,
     * or with status 400 (Bad Request) if the countryPathDTO is not valid,
     * or with status 500 (Internal Server Error) if the countryPathDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/country-paths")
    @Timed
    public ResponseEntity<CountryPathDTO> updateCountryPath(@RequestBody CountryPathDTO countryPathDTO) throws URISyntaxException {
        log.debug("REST request to update CountryPath : {}", countryPathDTO);
        if (countryPathDTO.getId() == null) {
            return createCountryPath(countryPathDTO);
        }
        CountryPath countryPath = countryPathMapper.toEntity(countryPathDTO);
        countryPath = countryPathRepository.save(countryPath);
        CountryPathDTO result = countryPathMapper.toDto(countryPath);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countryPathDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /country-paths : get all the countryPaths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countryPaths in body
     */
    @GetMapping("/country-paths")
    @Timed
    public List<CountryPathDTO> getAllCountryPaths() {
        log.debug("REST request to get all CountryPaths");
        List<CountryPath> countryPaths = countryPathRepository.findAll();
        return countryPathMapper.toDto(countryPaths);
        }

    /**
     * GET  /country-paths/:id : get the "id" countryPath.
     *
     * @param id the id of the countryPathDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countryPathDTO, or with status 404 (Not Found)
     */
    @GetMapping("/country-paths/{id}")
    @Timed
    public ResponseEntity<CountryPathDTO> getCountryPath(@PathVariable String id) {
        log.debug("REST request to get CountryPath : {}", id);
        CountryPath countryPath = countryPathRepository.findOne(id);
        CountryPathDTO countryPathDTO = countryPathMapper.toDto(countryPath);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countryPathDTO));
    }

    /**
     * DELETE  /country-paths/:id : delete the "id" countryPath.
     *
     * @param id the id of the countryPathDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/country-paths/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountryPath(@PathVariable String id) {
        log.debug("REST request to delete CountryPath : {}", id);
        countryPathRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
