package de.skillspot.mapper;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.entity.AnbieterEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T21:45:46+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class AnbieterMapperImpl implements AnbieterMapper {

    @Override
    public AnbieterDto toDto(AnbieterEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AnbieterDto.AnbieterDtoBuilder anbieterDto = AnbieterDto.builder();

        anbieterDto.anbieterId( entity.getAnbieterId() );
        anbieterDto.firmenName( entity.getFirmenName() );
        anbieterDto.beschreibung( entity.getBeschreibung() );
        anbieterDto.locationLat( entity.getLocationLat() );
        anbieterDto.locationLon( entity.getLocationLon() );

        return anbieterDto.build();
    }
}
