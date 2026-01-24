package de.skillspot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeIntegrationTest extends AbstractIntegrationTest {

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
        jdbcTemplate.execute("DELETE FROM skillspot.anbieter");
        jdbcTemplate.execute("DELETE FROM skillspot.benutzer");
    }

    @Test
    void testGetCurrentUserNotExists() throws Exception {
        mockMvc.perform(get("/me")
                        .with(jwt().jwt(j -> j.claim("sub", "auth0|newuser").claim("email", "new@example.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sub").value("auth0|newuser"))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.displayName").isEmpty());
    }

    @Test
    void testCompleteProfileAsUser() throws Exception {
        String sub = "auth0|user123";
        String jsonRequest = "{\"displayName\": \"John Doe\", \"role\": \"USER\"}";

        mockMvc.perform(post("/me/complete-profile")
                        .with(jwt().jwt(j -> j.claim("sub", sub).claim("email", "john@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("John Doe"))
                .andExpect(jsonPath("$.role").value("USER"));

        // Verify in DB
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM skillspot.benutzer WHERE auth0_sub = ?", Integer.class, sub);
        assert count != null && count == 1;
    }

    @Test
    void testCompleteProfileAsProvider() throws Exception {
        String sub = "auth0|provider123";
        String jsonRequest = "{\"displayName\": \"Best Service\", \"role\": \"PROVIDER\", \"locationLat\": 52.52, \"locationLon\": 13.40}";

        mockMvc.perform(post("/me/complete-profile")
                        .with(jwt().jwt(j -> j.claim("sub", sub).claim("email", "provider@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Best Service"))
                .andExpect(jsonPath("$.role").value("PROVIDER"));

        // Verify in DB
        Integer userCount = jdbcTemplate.queryForObject("SELECT count(*) FROM skillspot.benutzer WHERE auth0_sub = ?", Integer.class, sub);
        assert userCount != null && userCount == 1;

        Integer providerCount = jdbcTemplate.queryForObject("SELECT count(*) FROM skillspot.anbieter a JOIN skillspot.benutzer b ON a.benutzer_id = b.benutzer_id WHERE b.auth0_sub = ?", Integer.class, sub);
        assert providerCount != null && providerCount == 1;
    }

    @Test
    void testUpdateProfile() throws Exception {
        String sub = "auth0|updateuser";
        jdbcTemplate.update("INSERT INTO skillspot.benutzer (auth0_sub, display_name, rolle) VALUES (?, ?, ?)", sub, "Old Name", "USER");

        String jsonRequest = "{\"displayName\": \"New Name\", \"role\": \"USER\"}";

        mockMvc.perform(put("/me/profile")
                        .with(jwt().jwt(j -> j.claim("sub", sub).claim("email", "update@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("New Name"));

        // Verify in DB
        String name = jdbcTemplate.queryForObject("SELECT display_name FROM skillspot.benutzer WHERE auth0_sub = ?", String.class, sub);
        assert "New Name".equals(name);
    }
}
