package de.skillspot.mapper;

import de.skillspot.dto.DienstleistungDto;
import de.skillspot.entity.DienstleistungEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T20:25:20+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class DienstleistungMapperImpl implements DienstleistungMapper {

    @Override
    public DienstleistungDto toDto(DienstleistungEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DienstleistungDto.DienstleistungDtoBuilder dienstleistungDto = DienstleistungDto.builder();

        dienstleistungDto.dienstleistungId( entity.getDienstleistungId() );
        dienstleistungDto.anbieterId( entity.getAnbieterId() );
        dienstleistungDto.kategorieId( entity.getKategorieId() );
        dienstleistungDto.title( entity.getTitle() );
        dienstleistungDto.beschreibung( entity.getBeschreibung() );
        if ( entity.getPreis() != null ) {
            dienstleistungDto.preis( entity.getPreis().doubleValue() );
        }

        return dienstleistungDto.build();
    }
}
