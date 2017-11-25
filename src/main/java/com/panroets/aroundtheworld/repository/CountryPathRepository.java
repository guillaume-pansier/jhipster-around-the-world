package com.panroets.aroundtheworld.repository;

import com.panroets.aroundtheworld.domain.CountryPath;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CountryPath entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryPathRepository extends MongoRepository<CountryPath, String> {

}
