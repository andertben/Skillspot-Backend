package de.skillspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DienstleistungResponse {
    private Long dienstleistungId;
    private String title;
    private Double preis;
    private Long kategorieId;
}
