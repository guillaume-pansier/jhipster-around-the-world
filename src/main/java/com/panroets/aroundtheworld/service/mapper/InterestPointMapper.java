package com.panroets.aroundtheworld.service.mapper;

import com.panroets.aroundtheworld.domain.*;
import com.panroets.aroundtheworld.service.dto.InterestPointDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InterestPoint and its DTO InterestPointDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InterestPointMapper extends EntityMapper<InterestPointDTO, InterestPoint> {

    

    
}
