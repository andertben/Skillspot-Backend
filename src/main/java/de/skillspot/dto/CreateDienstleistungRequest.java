package de.skillspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDienstleistungRequest {
    private Long kategorieId;
    private String title;
    private String beschreibung;
    private Double preis;
}
