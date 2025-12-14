package de.skillspot.mapper;

import de.skillspot.dto.BewertungDto;
import de.skillspot.entity.BewertungEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BewertungMapper {

    BewertungDto toDto(BewertungEntity entity);
}
