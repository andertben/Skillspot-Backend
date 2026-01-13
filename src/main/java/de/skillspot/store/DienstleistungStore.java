package de.skillspot.store;

import de.skillspot.entity.DienstleistungEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DienstleistungStore {

    private final JdbcTemplate jdbcTemplate;

    public DienstleistungStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DienstleistungEntity> loadServices() {
        return jdbcTemplate.query(
                "SELECT * FROM dienstleistung;",
                (row, rowNum) -> DienstleistungEntity.builder()
                        .dienstleistungId(row.getLong("dienstleistung_id"))
                        .anbieterId(row.getLong("anbieter_id"))
                        .kategorieId(row.getLong("kategorie_id"))
                        .title(row.getString("title"))
                        .beschreibung(row.getString("beschreibung"))
                        .preis(row.getObject("preis") != null ? row.getDouble("preis") : null)
                        .build()
        );
    }
}
