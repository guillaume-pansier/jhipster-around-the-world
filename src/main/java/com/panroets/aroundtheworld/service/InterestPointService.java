package com.panroets.aroundtheworld.service;

import com.panroets.aroundtheworld.service.dto.InterestPointDTO;
import java.util.List;

/**
 * Service Interface for managing InterestPoint.
 */
public interface InterestPointService {

    /**
     * Save a interestPoint.
     *
     * @param interestPointDTO the entity to save
     * @return the persisted entity
     */
    InterestPointDTO save(InterestPointDTO interestPointDTO);

    /**
     *  Get all the interestPoints.
     *
     *  @return the list of entities
     */
    List<InterestPointDTO> findAll();

    /**
     *  Get the "id" interestPoint.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InterestPointDTO findOne(String id);

    /**
     *  Delete the "id" interestPoint.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
