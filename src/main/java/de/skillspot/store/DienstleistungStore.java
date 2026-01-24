package de.skillspot.store;

import de.skillspot.entity.DienstleistungEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class DienstleistungStore {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<DienstleistungEntity> dienstleistungRowMapper = (row, rowNum) ->
            DienstleistungEntity.builder()
                    .dienstleistungId(row.getLong("dienstleistung_id"))
                    .anbieterId(row.getLong("anbieter_id"))
                    .kategorieId(row.getLong("kategorie_id"))
                    .title(row.getString("title"))
                    .beschreibung(row.getString("beschreibung"))
                    .preis(row.getBigDecimal("preis"))
                    .build();

    public DienstleistungStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DienstleistungEntity> loadServices() {
        return jdbcTemplate.query("SELECT * FROM skillspot.dienstleistung;", dienstleistungRowMapper);
    }

    public List<DienstleistungEntity> loadByAnbieterId(Long anbieterId) {
        return jdbcTemplate.query(
                "SELECT * FROM skillspot.dienstleistung WHERE anbieter_id = ?;",
                dienstleistungRowMapper,
                anbieterId
        );
    }

    public java.util.Optional<DienstleistungEntity> findById(Long id) {
        List<DienstleistungEntity> results = jdbcTemplate.query(
                "SELECT * FROM skillspot.dienstleistung WHERE dienstleistung_id = ?;",
                dienstleistungRowMapper,
                id
        );
        return results.stream().findFirst();
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM skillspot.dienstleistung WHERE dienstleistung_id = ?;", id);
    }

    public DienstleistungEntity save(DienstleistungEntity entity) {
        String sql = "INSERT INTO skillspot.dienstleistung (anbieter_id, kategorie_id, title, beschreibung) VALUES (?, ?, ?, ?) RETURNING dienstleistung_id";

        Long generatedId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                entity.getAnbieterId(),
                entity.getKategorieId(),
                entity.getTitle(),
                entity.getBeschreibung()
        );

        if (generatedId != null) {
            entity.setDienstleistungId(generatedId);
        }
        return entity;
    }
}
