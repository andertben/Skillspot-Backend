package de.skillspot;

import de.skillspot.entity.KategorieEntity;
import de.skillspot.store.KategorieStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class KategorieI18nIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private KategorieStore kategorieStore;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        jdbcTemplate.execute("DELETE FROM skillspot.kategorie_i18n");
        jdbcTemplate.execute("DELETE FROM skillspot.kategorie");
    }

    @Test
    void testI18nFallback() {
        // 1. Setup Test Data (using a high ID to avoid conflicts with seeds)
        Long testId = 9999L;
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung, icon) VALUES (" + testId + ", 'Deutsch Name', 'icon')");
        // No English translation yet

        // 2. Test Fallback (requesting 'en' should return 'Deutsch Name' because of COALESCE)
        Optional<KategorieEntity> cat = kategorieStore.findById(testId, "en");
        assertThat(cat).isPresent();
        assertThat(cat.get().getBezeichnung()).isEqualTo("Deutsch Name");

        // 3. Add English Translation
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie_i18n (kategorie_id, lang, bezeichnung) VALUES (" + testId + ", 'en', 'English Name')");

        // 4. Test English Translation
        cat = kategorieStore.findById(testId, "en");
        assertThat(cat).isPresent();
        assertThat(cat.get().getBezeichnung()).isEqualTo("English Name");
        
        // 5. Still German for 'de'
        cat = kategorieStore.findById(testId, "de");
        assertThat(cat).isPresent();
        assertThat(cat.get().getBezeichnung()).isEqualTo("Deutsch Name");
    }

    @Test
    void testCategoryTree() throws Exception {
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (1, 'Parent 1')");
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id) VALUES (2, 'Child 1-1', 1)");
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id) VALUES (3, 'Child 1-2', 1)");
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (4, 'Parent 2')");

        mockMvc.perform(get("/kategorien/tree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Parent 1"))
                .andExpect(jsonPath("$[0].children.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Parent 2"))
                .andExpect(jsonPath("$[1].children.length()").value(0));
    }
}
