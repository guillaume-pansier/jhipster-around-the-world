package com.panroets.aroundtheworld.service.mapper;

import com.panroets.aroundtheworld.domain.*;
import com.panroets.aroundtheworld.service.dto.TripDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trip and its DTO TripDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripMapper extends EntityMapper<TripDTO, Trip> {

    

    
}
