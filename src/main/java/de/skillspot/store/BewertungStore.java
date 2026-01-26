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
                "SELECT * FROM skillspot.bewertung ORDER BY erstellungsdatum DESC;",
                (row, rowNum) -> mapRowToEntity(row)
        );
    }

    public List<BewertungEntity> findByServiceId(Long serviceId) {
        return jdbcTemplate.query(
                "SELECT * FROM skillspot.bewertung WHERE dienstleistung_id = ? ORDER BY erstellungsdatum DESC;",
                (row, rowNum) -> mapRowToEntity(row),
                serviceId
        );
    }

    public List<BewertungEntity> findByProviderId(Long providerId) {
        return jdbcTemplate.query(
                "SELECT * FROM skillspot.bewertung WHERE anbieter_id = ? ORDER BY erstellungsdatum DESC;",
                (row, rowNum) -> mapRowToEntity(row),
                providerId
        );
    }

    public Double getAverageRatingByServiceId(Long serviceId) {
        return jdbcTemplate.queryForObject(
                "SELECT AVG(bewertung) FROM skillspot.bewertung WHERE dienstleistung_id = ?;",
                Double.class,
                serviceId
        );
    }

    public java.util.Optional<BewertungEntity> findById(Long id) {
        List<BewertungEntity> results = jdbcTemplate.query(
                "SELECT * FROM skillspot.bewertung WHERE bewertung_id = ?;",
                (row, rowNum) -> mapRowToEntity(row),
                id
        );
        return results.stream().findFirst();
    }

    public Long save(BewertungEntity entity) {
        String sql = "INSERT INTO skillspot.bewertung (dienstleistung_id, benutzer_id, anbieter_id, bewertung, text, erstellungsdatum) " +
                "VALUES (?, ?, ?, ?, ?, NOW()) RETURNING bewertung_id";

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                entity.getDienstleistungId(),
                entity.getBenutzerId(),
                entity.getAnbieterId(),
                entity.getBewertung(),
                entity.getText()
        );
    }

    private BewertungEntity mapRowToEntity(java.sql.ResultSet row) throws java.sql.SQLException {
        return BewertungEntity.builder()
                .bewertungId(row.getLong("bewertung_id"))
                .dienstleistungId(row.getLong("dienstleistung_id"))
                .benutzerId(row.getLong("benutzer_id"))
                .anbieterId(row.getObject("anbieter_id") != null ? row.getLong("anbieter_id") : null)
                .bewertung(row.getObject("bewertung") != null ? row.getInt("bewertung") : null)
                .text(row.getString("text"))
                .erstellungsDatum(row.getTimestamp("erstellungsdatum") != null ? row.getTimestamp("erstellungsdatum").toLocalDateTime() : null)
                .build();
    }
}
