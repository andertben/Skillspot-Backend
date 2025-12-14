package de.skillspot.store;

import de.skillspot.entity.KategorieEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorieStore {
    private final JdbcTemplate jdbcTemplate;

    public KategorieStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KategorieEntity> loadcategories(){
        return jdbcTemplate.query("SELECT * FROM kategorie;",(row, rowNum) ->
                KategorieEntity.builder()
                .kategorie_id(row.getLong("kategorie_id"))
                .bezeichnung(row.getString("bezeichnung"))
                        .oberkategorie_id(row.getLong("oberkategorie_id"))
                        .icon(row.getString("icon"))
                .build());
    }
}
