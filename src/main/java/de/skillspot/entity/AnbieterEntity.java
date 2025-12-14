package de.skillspot.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnbieterEntity {
    Long anbieterId;
    Long benutzerId;
    String firmenName;
    String beschreibung;
    Double locationLat;
    Double locationLon;
}
