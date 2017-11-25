package com.panroets.aroundtheworld.service;

import com.panroets.aroundtheworld.service.dto.TripDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Trip.
 */
public interface TripService {

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save
     * @return the persisted entity
     */
    TripDTO save(TripDTO tripDTO);

    /**
     *  Get all the trips.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TripDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" trip.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TripDTO findOne(String id);

    /**
     *  Delete the "id" trip.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
