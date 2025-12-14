package de.skillspot.mapper;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.entity.AnbieterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnbieterMapper {

    AnbieterDto toDto(AnbieterEntity entity);
}
