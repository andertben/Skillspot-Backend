package de.skillspot.mapper;

import de.skillspot.dto.DienstleistungDto;
import de.skillspot.entity.DienstleistungEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DienstleistungMapper {

    DienstleistungDto toDto(DienstleistungEntity entity);
}
