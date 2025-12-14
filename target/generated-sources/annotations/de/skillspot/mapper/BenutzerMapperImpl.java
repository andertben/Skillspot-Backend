package de.skillspot.mapper;

import de.skillspot.dto.BenutzerDto;
import de.skillspot.entity.BenutzerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T13:16:27+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class BenutzerMapperImpl implements BenutzerMapper {

    @Override
    public BenutzerDto toDto(BenutzerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BenutzerDto.BenutzerDtoBuilder benutzerDto = BenutzerDto.builder();

        benutzerDto.benutzerId( entity.getBenutzerId() );
        benutzerDto.vorname( entity.getVorname() );
        benutzerDto.nachname( entity.getNachname() );
        benutzerDto.email( entity.getEmail() );

        return benutzerDto.build();
    }
}
