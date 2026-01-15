package de.skillspot.mapper;

import de.skillspot.dto.KategorieDto;
import de.skillspot.entity.KategorieEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T21:45:46+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class KategorieMapperImpl implements KategorieMapper {

    @Override
    public KategorieDto toDto(KategorieEntity entity) {
        if ( entity == null ) {
            return null;
        }

        KategorieDto.KategorieDtoBuilder kategorieDto = KategorieDto.builder();

        kategorieDto.kategorie_id( entity.getKategorie_id() );
        kategorieDto.bezeichnung( entity.getBezeichnung() );
        kategorieDto.oberkategorie_id( entity.getOberkategorie_id() );
        kategorieDto.icon( entity.getIcon() );

        return kategorieDto.build();
    }
}
