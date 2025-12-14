package de.skillspot.store;

import de.skillspot.entity.BewertungEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BewertungStore {

    private final JdbcTemplate jdbcTemplate;

    public BewertungStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BewertungEntity> loadReviews() {
        return jdbcTemplate.query(
                "SELECT * FROM bewertung;",
                (row, rowNum) -> BewertungEntity.builder()
                        .bewertungId(row.getLong("bewertung_id"))
                        .dienstleistungId(row.getLong("dienstleistung_id"))
                        .benutzerId(row.getLong("benutzer_id"))
                        .anbieterId(row.getLong("anbieter_id"))
                        .buchungId(row.getLong("buchung_id"))
                        .bewertung(row.getInt("bewertung"))
                        .text(row.getString("text"))
                        .erstellungsDatum(row.getTimestamp("erstellungs_datum").toLocalDateTime())
                        .build()
        );
    }
}
