package de.skillspot.store;

import de.skillspot.entity.BenutzerEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenutzerStore {

    private final JdbcTemplate jdbcTemplate;

    public BenutzerStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BenutzerEntity> loadUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM benutzer;",
                (row, rowNum) -> BenutzerEntity.builder()
                        .benutzerId(row.getLong("benutzer_id"))
                        .vorname(row.getString("vorname"))
                        .nachname(row.getString("nachname"))
                        .email(row.getString("email"))
                        .passwordHash(row.getString("password_hash"))
                        .build()
        );
    }
}
