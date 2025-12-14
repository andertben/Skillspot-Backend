package de.skillspot.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuchungEntity {
    Long buchungId;
    Long dienstleistungId;
    Long benutzerId;
    String text;
    LocalDateTime anfrageDatum;
    String status;
    Double preis;
}
