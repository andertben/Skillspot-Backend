package de.skillspot.mapper;

import de.skillspot.dto.KategorieDto;
import de.skillspot.entity.KategorieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KategorieMapper {

    KategorieDto toDto(KategorieEntity entity);

}
