package com.panroets.aroundtheworld.service.impl;

import com.panroets.aroundtheworld.service.InterestPointService;
import com.panroets.aroundtheworld.domain.InterestPoint;
import com.panroets.aroundtheworld.repository.InterestPointRepository;
import com.panroets.aroundtheworld.service.dto.InterestPointDTO;
import com.panroets.aroundtheworld.service.mapper.InterestPointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InterestPoint.
 */
@Service
public class InterestPointServiceImpl implements InterestPointService{

    private final Logger log = LoggerFactory.getLogger(InterestPointServiceImpl.class);

    private final InterestPointRepository interestPointRepository;

    private final InterestPointMapper interestPointMapper;

    public InterestPointServiceImpl(InterestPointRepository interestPointRepository, InterestPointMapper interestPointMapper) {
        this.interestPointRepository = interestPointRepository;
        this.interestPointMapper = interestPointMapper;
    }

    /**
     * Save a interestPoint.
     *
     * @param interestPointDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InterestPointDTO save(InterestPointDTO interestPointDTO) {
        log.debug("Request to save InterestPoint : {}", interestPointDTO);
        InterestPoint interestPoint = interestPointMapper.toEntity(interestPointDTO);
        interestPoint = interestPointRepository.save(interestPoint);
        return interestPointMapper.toDto(interestPoint);
    }

    /**
     *  Get all the interestPoints.
     *
     *  @return the list of entities
     */
    @Override
    public List<InterestPointDTO> findAll() {
        log.debug("Request to get all InterestPoints");
        return interestPointRepository.findAll().stream()
            .map(interestPointMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one interestPoint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public InterestPointDTO findOne(String id) {
        log.debug("Request to get InterestPoint : {}", id);
        InterestPoint interestPoint = interestPointRepository.findOne(id);
        return interestPointMapper.toDto(interestPoint);
    }

    /**
     *  Delete the  interestPoint by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete InterestPoint : {}", id);
        interestPointRepository.delete(id);
    }
}
