package de.skillspot.store;

import de.skillspot.entity.AnbieterEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

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
                    .locationLat(row.getObject("location_lat") != null ? row.getDouble("location_lat") : null)
                    .locationLon(row.getObject("location_lon") != null ? row.getDouble("location_lon") : null)
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
}
