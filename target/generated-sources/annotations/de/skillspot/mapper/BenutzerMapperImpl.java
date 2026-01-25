package de.skillspot.mapper;

import de.skillspot.dto.BenutzerDto;
import de.skillspot.entity.BenutzerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T18:16:31+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Homebrew)"
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
