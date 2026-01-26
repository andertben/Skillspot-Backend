package de.skillspot.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BewertungEntity {
    Long bewertungId;
    Long dienstleistungId;
    Long benutzerId;
    Long anbieterId;
    Integer bewertung;
    String text;
    LocalDateTime erstellungsDatum;
}
