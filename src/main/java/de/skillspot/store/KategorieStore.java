package de.skillspot.store;

import de.skillspot.entity.KategorieEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategorieStore {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<KategorieEntity> kategorieRowMapper = (row, rowNum) ->
            KategorieEntity.builder()
                    .kategorie_id(row.getLong("kategorie_id"))
                    .bezeichnung(row.getString("bezeichnung"))
                    .oberkategorie_id(row.getObject("oberkategorie_id") != null ? row.getLong("oberkategorie_id") : null)
                    .icon(row.getString("icon"))
                    .build();

    public KategorieStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KategorieEntity> loadcategories(){
        return jdbcTemplate.query("SELECT * FROM skillspot.kategorie;", kategorieRowMapper);
    }

    public Optional<KategorieEntity> findById(Long id) {
        List<KategorieEntity> results = jdbcTemplate.query(
                "SELECT * FROM skillspot.kategorie WHERE kategorie_id = ?;",
                kategorieRowMapper,
                id
        );
        return results.stream().findFirst();
    }
}
