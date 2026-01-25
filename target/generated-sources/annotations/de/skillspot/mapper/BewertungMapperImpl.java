package de.skillspot.mapper;

import de.skillspot.dto.BewertungDto;
import de.skillspot.entity.BewertungEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T20:25:20+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class BewertungMapperImpl implements BewertungMapper {

    @Override
    public BewertungDto toDto(BewertungEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BewertungDto.BewertungDtoBuilder bewertungDto = BewertungDto.builder();

        bewertungDto.bewertungId( entity.getBewertungId() );
        bewertungDto.dienstleistungId( entity.getDienstleistungId() );
        bewertungDto.benutzerId( entity.getBenutzerId() );
        bewertungDto.anbieterId( entity.getAnbieterId() );
        bewertungDto.buchungId( entity.getBuchungId() );
        bewertungDto.bewertung( entity.getBewertung() );
        bewertungDto.text( entity.getText() );
        bewertungDto.erstellungsDatum( entity.getErstellungsDatum() );

        return bewertungDto.build();
    }
}
