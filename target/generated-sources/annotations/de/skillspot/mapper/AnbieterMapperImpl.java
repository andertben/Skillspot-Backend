package de.skillspot.mapper;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.entity.AnbieterEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T15:45:20+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
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
