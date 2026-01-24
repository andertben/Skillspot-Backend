package de.skillspot;

import de.skillspot.dto.CreateDienstleistungRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DienstleistungIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        jdbcTemplate.execute("DELETE FROM skillspot.dienstleistung");
        jdbcTemplate.execute("DELETE FROM skillspot.anbieter");
        jdbcTemplate.execute("DELETE FROM skillspot.benutzer");
        jdbcTemplate.execute("DELETE FROM skillspot.kategorie");
    }

    @Test
    void testLoadServices() throws Exception {
        mockMvc.perform(get("/dienstleistungen"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateDienstleistung() throws Exception {
        String sub = "auth0|provider1";
        
        // Seed user and anbieter
        jdbcTemplate.update("INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle) VALUES (?, ?, ?)", sub, "Provider 1", "PROVIDER");
        Long benutzerId = jdbcTemplate.queryForObject("SELECT benutzer_id FROM skillspot.benutzer WHERE auth0_sub = ?", Long.class, sub);
        jdbcTemplate.update("INSERT INTO skillspot.anbieter (benutzer_id, firmen_name) VALUES (?, ?)", benutzerId, "Firma 1");
        
        // Seed categories
        jdbcTemplate.update("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (1, 'Main Category')");
        jdbcTemplate.update("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung, oberkategorie_id) VALUES (2, 'Sub Category', 1)");

        CreateDienstleistungRequest request = CreateDienstleistungRequest.builder()
                .title("New Service")
                .beschreibung("Description")
                .kategorieId(2L)
                .build();

        mockMvc.perform(post("/services")
                        .with(jwt().jwt(j -> j.claim("sub", sub)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Service"))
                .andExpect(jsonPath("$.dienstleistungId").exists());
    }

    @Test
    void testDeleteDienstleistung() throws Exception {
        String sub = "auth0|provider2";
        
        // Seed everything
        jdbcTemplate.update("INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle) VALUES (?, ?, ?)", sub, "Provider 2", "PROVIDER");
        Long benutzerId = jdbcTemplate.queryForObject("SELECT benutzer_id FROM skillspot.benutzer WHERE auth0_sub = ?", Long.class, sub);
        jdbcTemplate.update("INSERT INTO skillspot.anbieter (anbieter_id, benutzer_id, firmen_name) VALUES (100, ?, ?)", benutzerId, "Firma 2");
        jdbcTemplate.update("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (10, 'Cat')");
        jdbcTemplate.update("INSERT INTO skillspot.dienstleistung (dienstleistung_id, anbieter_id, kategorie_id, title) VALUES (500, 100, 10, 'Service to delete')");

        mockMvc.perform(delete("/services/500")
                        .with(jwt().jwt(j -> j.claim("sub", sub))))
                .andExpect(status().isNoContent());

        // Verify deletion
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM skillspot.dienstleistung WHERE dienstleistung_id = 500", Integer.class);
        assert count != null && count == 0;
    }
}
