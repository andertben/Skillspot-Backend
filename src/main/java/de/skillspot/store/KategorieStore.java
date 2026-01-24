package de.skillspot.store;

import de.skillspot.entity.KategorieEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public List<KategorieEntity> loadcategories(String lang) {
        Integer i18nCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM skillspot.kategorie_i18n WHERE lang = ?", Integer.class, lang);
        log.info("Found {} translation entries in skillspot.kategorie_i18n for language '{}'", i18nCount, lang);

        String sql = "SELECT k.kategorie_id, COALESCE(ki.bezeichnung, k.bezeichnung) AS bezeichnung, k.oberkategorie_id, k.icon " +
                     "FROM skillspot.kategorie k " +
                     "LEFT JOIN skillspot.kategorie_i18n ki ON ki.kategorie_id = k.kategorie_id AND ki.lang = ?;";
        return jdbcTemplate.query(sql, kategorieRowMapper, lang);
    }

    public Optional<KategorieEntity> findById(Long id) {
        return findById(id, "de");
    }

    public Optional<KategorieEntity> findById(Long id, String lang) {
        String sql = "SELECT k.kategorie_id, COALESCE(ki.bezeichnung, k.bezeichnung) AS bezeichnung, k.oberkategorie_id, k.icon " +
                     "FROM skillspot.kategorie k " +
                     "LEFT JOIN skillspot.kategorie_i18n ki ON ki.kategorie_id = k.kategorie_id AND ki.lang = ? " +
                     "WHERE k.kategorie_id = ?;";
        List<KategorieEntity> results = jdbcTemplate.query(sql, kategorieRowMapper, lang, id);
        return results.stream().findFirst();
    }
}
