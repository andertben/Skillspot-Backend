package de.skillspot.mapper;

import de.skillspot.dto.BuchungDto;
import de.skillspot.entity.BuchungEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuchungMapper {

    BuchungDto toDto(BuchungEntity entity);
}
