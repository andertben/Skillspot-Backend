package de.skillspot.mapper;

import de.skillspot.dto.BuchungDto;
import de.skillspot.entity.BuchungEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T18:14:29+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Homebrew)"
)
@Component
public class BuchungMapperImpl implements BuchungMapper {

    @Override
    public BuchungDto toDto(BuchungEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BuchungDto.BuchungDtoBuilder buchungDto = BuchungDto.builder();

        buchungDto.buchungId( entity.getBuchungId() );
        buchungDto.dienstleistungId( entity.getDienstleistungId() );
        buchungDto.benutzerId( entity.getBenutzerId() );
        buchungDto.text( entity.getText() );
        buchungDto.anfrageDatum( entity.getAnfrageDatum() );
        buchungDto.status( entity.getStatus() );
        buchungDto.preis( entity.getPreis() );

        return buchungDto.build();
    }
}
