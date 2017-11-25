package com.panroets.aroundtheworld.repository;

import com.panroets.aroundtheworld.domain.InterestPoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the InterestPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterestPointRepository extends MongoRepository<InterestPoint, String> {

}
