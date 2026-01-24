package de.skillspot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDienstleistungRequest {
    @NotNull(message = "Kategorie ID is required")
    @JsonAlias("categoryId")
    private Long kategorieId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Beschreibung is required")
    @JsonAlias("description")
    private String beschreibung;

    @JsonAlias("basePrice")
    private BigDecimal preis;
}
