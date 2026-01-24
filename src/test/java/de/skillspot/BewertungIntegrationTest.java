package de.skillspot;

import de.skillspot.dto.CreateBewertungRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BewertungIntegrationTest extends AbstractIntegrationTest {

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
        
        jdbcTemplate.execute("DELETE FROM skillspot.bewertung");
        jdbcTemplate.execute("DELETE FROM skillspot.dienstleistung");
        jdbcTemplate.execute("DELETE FROM skillspot.anbieter");
        jdbcTemplate.execute("DELETE FROM skillspot.benutzer");
        jdbcTemplate.execute("DELETE FROM skillspot.kategorie");
    }

    @Test
    void testCreateBewertung() throws Exception {
        String sub = "auth0|reviewer";
        
        // Seed user
        jdbcTemplate.update("INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle) VALUES (?, ?, ?)", sub, "Reviewer", "USER");
        
        // Seed provider and service
        jdbcTemplate.update("INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle) VALUES (?, ?, ?)", "auth0|p", "P", "PROVIDER");
        Long providerBenutzerId = jdbcTemplate.queryForObject("SELECT benutzer_id FROM skillspot.benutzer WHERE auth0_sub = 'auth0|p'", Long.class);
        jdbcTemplate.update("INSERT INTO skillspot.anbieter (anbieter_id, benutzer_id, firmen_name) VALUES (1, ?, ?)", providerBenutzerId, "Firma");
        jdbcTemplate.update("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (1, 'Cat')");
        jdbcTemplate.update("INSERT INTO skillspot.dienstleistung (dienstleistung_id, anbieter_id, kategorie_id, title) VALUES (1, 1, 1, 'Service')");

        CreateBewertungRequest request = CreateBewertungRequest.builder()
                .dienstleistungId(1L)
                .bewertung(5)
                .text("Excellent!")
                .build();

        mockMvc.perform(post("/reviews")
                        .with(jwt().jwt(j -> j.claim("sub", sub)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Excellent!"))
                .andExpect(jsonPath("$.bewertung").value(5));
    }

    @Test
    void testGetAverageRating() throws Exception {
        jdbcTemplate.update("INSERT INTO skillspot.kategorie (kategorie_id, bezeichnung) VALUES (1, 'Cat')");
        jdbcTemplate.update("INSERT INTO skillspot.dienstleistung (dienstleistung_id, anbieter_id, kategorie_id, title) VALUES (10, 1, 1, 'Service')");
        
        jdbcTemplate.update("INSERT INTO skillspot.bewertung (dienstleistung_id, bewertung) VALUES (10, 4)");
        jdbcTemplate.update("INSERT INTO skillspot.bewertung (dienstleistung_id, bewertung) VALUES (10, 5)");

        mockMvc.perform(get("/bewertungen/average/10")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }
}
