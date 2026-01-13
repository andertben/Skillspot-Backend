package de.skillspot.store;

import de.skillspot.entity.BuchungEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuchungStore {

    private final JdbcTemplate jdbcTemplate;

    public BuchungStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BuchungEntity> loadBookings() {
        return jdbcTemplate.query(
                "SELECT * FROM buchung;",
                (row, rowNum) -> BuchungEntity.builder()
                        .buchungId(row.getLong("buchung_id"))
                        .dienstleistungId(row.getLong("dienstleistung_id"))
                        .benutzerId(row.getLong("benutzer_id"))
                        .text(row.getString("text"))
                        .anfrageDatum(row.getTimestamp("anfragedatum") != null ? row.getTimestamp("anfragedatum").toLocalDateTime() : null)
                        .status(row.getString("status"))
                        .preis(row.getObject("preis") != null ? row.getDouble("preis") : null)
                        .build()
        );
    }
}
