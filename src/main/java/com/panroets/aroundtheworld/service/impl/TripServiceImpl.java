package com.panroets.aroundtheworld.service.impl;

import com.panroets.aroundtheworld.service.TripService;
import com.panroets.aroundtheworld.domain.Trip;
import com.panroets.aroundtheworld.repository.TripRepository;
import com.panroets.aroundtheworld.service.dto.TripDTO;
import com.panroets.aroundtheworld.service.mapper.TripMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Trip.
 */
@Service
public class TripServiceImpl implements TripService{

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    /**
     * Save a trip.
     *
     * @param tripDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TripDTO save(TripDTO tripDTO) {
        log.debug("Request to save Trip : {}", tripDTO);
        Trip trip = tripMapper.toEntity(tripDTO);
        trip = tripRepository.save(trip);
        return tripMapper.toDto(trip);
    }

    /**
     *  Get all the trips.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<TripDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        return tripRepository.findAll(pageable)
            .map(tripMapper::toDto);
    }

    /**
     *  Get one trip by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public TripDTO findOne(String id) {
        log.debug("Request to get Trip : {}", id);
        Trip trip = tripRepository.findOne(id);
        return tripMapper.toDto(trip);
    }

    /**
     *  Delete the  trip by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.delete(id);
    }
}
