package de.skillspot.store;

import de.skillspot.entity.AnbieterEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnbieterStore {

    private final JdbcTemplate jdbcTemplate;

    public AnbieterStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnbieterEntity> loadProviders() {
        return jdbcTemplate.query(
                "SELECT * FROM anbieter;",
                (row, rowNum) -> AnbieterEntity.builder()
                        .anbieterId(row.getLong("anbieter_id"))
                        .benutzerId(row.getLong("benutzer_id"))
                        .firmenName(row.getString("firmenname"))
                        .beschreibung(row.getString("beschreibung"))
                        .locationLat(row.getDouble("location_lat"))
                        .locationLon(row.getDouble("location_lon"))
                        .build()
        );
    }
}
