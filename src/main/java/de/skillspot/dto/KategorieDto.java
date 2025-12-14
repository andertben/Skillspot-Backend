package de.skillspot.dto;

import lombok.*;

@Data
@Builder
public class KategorieDto {
    Long kategorie_id;
    String bezeichnung;
    Long oberkategorie_id;
    String icon;
}
