package de.skillspot.controller;

import de.skillspot.entity.KategorieEntity;
import de.skillspot.store.KategorieStore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class KategorieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KategorieStore kategorieStore;

    @Test
    @Disabled
    void getKategorien_returnsDummyData() throws Exception {
        List<KategorieEntity> dummy = List.of(
                KategorieEntity.builder()
                        .kategorie_id(1L)
                        .bezeichnung("Sport")
                        .oberkategorie_id(null)
                        .icon("icon-sport")
                        .build(),
                KategorieEntity.builder()
                        .kategorie_id(2L)
                        .bezeichnung("Fitness")
                        .oberkategorie_id(1L)
                        .icon("icon-fitness")
                        .build()
        );

        when(kategorieStore.loadcategories("de")).thenReturn(dummy);

        mockMvc.perform(get("/kategorien").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].kategorie_id").value(1))
                .andExpect(jsonPath("$[0].bezeichnung").value("Sport"))
                .andExpect(jsonPath("$[0].oberkategorie_id").value(nullValue()))
                .andExpect(jsonPath("$[0].icon").value("icon-sport"))
                .andExpect(jsonPath("$[1].kategorie_id").value(2))
                .andExpect(jsonPath("$[1].bezeichnung").value("Fitness"))
                .andExpect(jsonPath("$[1].oberkategorie_id").value(1))
                .andExpect(jsonPath("$[1].icon").value("icon-fitness"));
    }
}
