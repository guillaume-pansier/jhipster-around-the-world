package com.panroets.aroundtheworld.service.mapper;

import com.panroets.aroundtheworld.domain.*;
import com.panroets.aroundtheworld.service.dto.CountryPathDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CountryPath and its DTO CountryPathDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryPathMapper extends EntityMapper<CountryPathDTO, CountryPath> {

    

    
}
