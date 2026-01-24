package de.skillspot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatThreadIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @org.springframework.transaction.annotation.Transactional
    void testCreateChatThreadWithAuth() throws Exception {
        // Setup minimal data
        Long benutzerId = 8888L;
        Long anbieterId = 8888L;
        Long katId = 8888L;

        jdbcTemplate.execute("INSERT INTO skillspot.benutzer (benutzer_id, auth0_sub, vorname, nachname, display_name) VALUES (" + benutzerId + ", 'auth0|provider', 'Provider', 'User', 'P-User')");
        jdbcTemplate.execute("INSERT INTO skillspot.anbieter (anbieter_id, benutzer_id, firmen_name) VALUES (" + anbieterId + ", " + benutzerId + ", 'Test Firma')");
        jdbcTemplate.execute("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung, icon) VALUES (" + katId + ", 'Kat', 'icon')");
        
        Long dlId = jdbcTemplate.queryForObject(
                "INSERT INTO skillspot.dienstleistung (anbieter_id, kategorie_id, title, beschreibung, preis) VALUES (?, ?, ?, ?, ?) RETURNING dienstleistung_id",
                Long.class,
                anbieterId, katId, "Test Service", "Desc", 100.0
        );

        System.out.println("DEBUG: Inserted Dienstleistung ID: " + dlId);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM skillspot.dienstleistung");
        System.out.println("DEBUG: Dienstleistung rows: " + rows);
        
        List<Map<String, Object>> anbieterRows = jdbcTemplate.queryForList("SELECT * FROM skillspot.anbieter");
        System.out.println("DEBUG: Anbieter rows: " + anbieterRows);

        String customerSub = "auth0|customer";
        String jsonRequest = "{\"dienstleistungId\": " + dlId + "}";

        mockMvc.perform(post("/chat/threads")
                        .with(jwt().jwt(j -> j.claim("sub", customerSub)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.threadId").exists())
                .andExpect(jsonPath("$.dienstleistungTitle").value("Test Service"))
                .andExpect(jsonPath("$.anbieterName").value("Test Firma"));
    }
}
