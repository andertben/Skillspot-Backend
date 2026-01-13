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
                        .anbieterId(row.getObject("anbieter_id") != null ? row.getLong("anbieter_id") : null)
                        .buchungId(row.getObject("buchung_id") != null ? row.getLong("buchung_id") : null)
                        .bewertung(row.getObject("bewertung") != null ? row.getInt("bewertung") : null)
                        .text(row.getString("text"))
                        .erstellungsDatum(row.getTimestamp("erstellungsdatum") != null ? row.getTimestamp("erstellungsdatum").toLocalDateTime() : null)
                        .build()
        );
    }
}
