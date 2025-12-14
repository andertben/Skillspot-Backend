package de.skillspot.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KategorieEntity {
    Long kategorie_id;
    String bezeichnung;
    Long oberkategorie_id;
    String icon;
}