package de.skillspot.store;

import de.skillspot.entity.CategoryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryStore {
    private final JdbcTemplate jdbcTemplate;

    public CategoryStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryEntity> loadcategories(){
        return jdbcTemplate.query("SELECT * FROM cat_categories;",(row, rowNum) ->
                CategoryEntity.builder()
                .categoryId(row.getLong("cat_id"))
                .text(row.getString("text"))
                .build());
    }
}
