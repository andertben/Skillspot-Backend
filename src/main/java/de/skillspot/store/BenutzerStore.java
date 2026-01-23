package de.skillspot.store;

import de.skillspot.dto.CompleteProfileRequest;
import de.skillspot.entity.BenutzerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BenutzerStore {

    private final JdbcTemplate jdbcTemplate;

    public BenutzerStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<BenutzerEntity> benutzerRowMapper = (row, rowNum) -> {
        BenutzerEntity.BenutzerEntityBuilder builder = BenutzerEntity.builder()
                .benutzerId(row.getLong("benutzer_id"))
                .vorname(row.getString("vorname"))
                .nachname(row.getString("nachname"))
                .email(row.getString("email"))
                .passwordHash(row.getString("password_hash"));

        try {
            builder.auth0Sub(row.getString("auth0_sub"))
                    .rolle(row.getString("rolle"))
                    .displayName(row.getString("display_name"))
                    .address(row.getString("address"))
                    .locationLat(row.getBigDecimal("location_lat"))
                    .locationLon(row.getBigDecimal("location_lon"))
                    .createdAt(row.getTimestamp("created_at") != null ? row.getTimestamp("created_at").toLocalDateTime() : null)
                    .updatedAt(row.getTimestamp("updated_at") != null ? row.getTimestamp("updated_at").toLocalDateTime() : null);
        } catch (Exception e) {
            // Falls Spalten noch nicht existieren (Migration noch nicht gelaufen)
            log.warn("Some profile columns are missing in skillspot.benutzer: {}", e.getMessage());
        }

        return builder.build();
    };

    public List<BenutzerEntity> loadUsers() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM skillspot.benutzer;",
                    benutzerRowMapper
            );
        } catch (DataAccessException e) {
            log.error("Failed to load users: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public Optional<BenutzerEntity> findByAuth0Sub(String sub) {
        try {
            List<BenutzerEntity> users = jdbcTemplate.query(
                    "SELECT * FROM skillspot.benutzer WHERE auth0_sub = ?;",
                    benutzerRowMapper,
                    sub
            );
            return users.stream().findFirst();
        } catch (DataAccessException e) {
            log.error("Database error in findByAuth0Sub (possibly missing columns): {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void upsertByAuth0Sub(String sub, String displayName, String role, String address, BigDecimal locationLat, BigDecimal locationLon) {
        String sql = """
                INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle, address, location_lat, location_lon, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, now())
                ON CONFLICT (auth0_sub) DO UPDATE SET
                    display_name = EXCLUDED.display_name,
                    rolle = EXCLUDED.rolle,
                    address = EXCLUDED.address,
                    location_lat = EXCLUDED.location_lat,
                    location_lon = EXCLUDED.location_lon,
                    updated_at = now();
                """;
        try {
            jdbcTemplate.update(sql, sub, displayName, role, address, locationLat, locationLon);
        } catch (DataAccessException e) {
            log.error("Failed to upsert user profile by auth0_sub: {}", e.getMessage());
            throw e; // Rethrow to allow controller advice to handle if needed
        }
    }
}
