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
                    .preis(row.getObject("preis") != null ? row.getDouble("preis") : null)
                    .build();

    public DienstleistungStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DienstleistungEntity> loadServices() {
        return jdbcTemplate.query("SELECT * FROM skillspot.dienstleistung;", dienstleistungRowMapper);
    }

    public DienstleistungEntity save(DienstleistungEntity entity) {
        String sql = "INSERT INTO skillspot.dienstleistung (anbieter_id, kategorie_id, title, beschreibung, preis) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, entity.getAnbieterId());
            ps.setLong(2, entity.getKategorieId());
            ps.setString(3, entity.getTitle());
            ps.setString(4, entity.getBeschreibung());
            if (entity.getPreis() != null) {
                ps.setDouble(5, entity.getPreis());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
            }
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            entity.setDienstleistungId(keyHolder.getKey().longValue());
        }
        return entity;
    }
}
