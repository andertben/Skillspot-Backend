package de.skillspot.mapper;

import de.skillspot.dto.BenutzerDto;
import de.skillspot.entity.BenutzerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BenutzerMapper {

    BenutzerDto toDto(BenutzerEntity entity);
}
