package com.panroets.aroundtheworld.service.mapper;

import com.panroets.aroundtheworld.domain.*;
import com.panroets.aroundtheworld.service.dto.CountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Country and its DTO CountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {

    

    
}
