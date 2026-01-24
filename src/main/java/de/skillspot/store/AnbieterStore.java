package de.skillspot.store;

import de.skillspot.entity.AnbieterEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AnbieterStore {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AnbieterEntity> anbieterRowMapper = (row, rowNum) ->
            AnbieterEntity.builder()
                    .anbieterId(row.getLong("anbieter_id"))
                    .benutzerId(row.getLong("benutzer_id"))
                    .firmenName(row.getString("firmen_name"))
                    .beschreibung(row.getString("beschreibung"))
                    .locationLat(row.getBigDecimal("location_lat"))
                    .locationLon(row.getBigDecimal("location_lon"))
                    .build();

    public AnbieterStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnbieterEntity> loadProviders() {
        return jdbcTemplate.query("SELECT * FROM skillspot.anbieter;", anbieterRowMapper);
    }

    public Optional<AnbieterEntity> findByBenutzerId(Long benutzerId) {
        List<AnbieterEntity> results = jdbcTemplate.query(
                "SELECT * FROM skillspot.anbieter WHERE benutzer_id = ?;",
                anbieterRowMapper,
                benutzerId
        );
        return results.stream().findFirst();
    }

    public void upsert(Long benutzerId, String firmenName, String beschreibung, BigDecimal locationLat, BigDecimal locationLon) {
        int updated = jdbcTemplate.update(
                "UPDATE skillspot.anbieter " +
                        "SET firmen_name = ?, beschreibung = ?, location_lat = ?, location_lon = ? " +
                        "WHERE benutzer_id = ?;",
                firmenName, beschreibung, locationLat, locationLon, benutzerId
        );

        if (updated == 0) {
            jdbcTemplate.update(
                    "INSERT INTO skillspot.anbieter (benutzer_id, firmen_name, beschreibung, location_lat, location_lon) " +
                            "VALUES (?, ?, ?, ?, ?);",
                    benutzerId, firmenName, beschreibung, locationLat, locationLon
            );
        }
    }
}
