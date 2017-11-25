package com.panroets.aroundtheworld.repository;

import com.panroets.aroundtheworld.domain.Trip;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Trip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TripRepository extends MongoRepository<Trip, String> {

}
