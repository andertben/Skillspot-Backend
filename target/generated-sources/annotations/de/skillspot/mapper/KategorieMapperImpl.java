package de.skillspot.mapper;

import de.skillspot.dto.KategorieDto;
import de.skillspot.entity.KategorieEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T17:35:51+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
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
