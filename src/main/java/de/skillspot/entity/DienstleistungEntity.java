package de.skillspot.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DienstleistungEntity {
    Long dienstleistungId;
    Long anbieterId;
    Long kategorieId;
    String title;
    String beschreibung;
    Double preis;
}
